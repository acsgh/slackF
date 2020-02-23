package acsgh.slack.infrastucture

import acsgh.slack.domain.SlackClient
import acsgh.slack.domain.model.{ConversationType, Conversations, User, UserPresence, Users}
import acsgh.slack.infrastucture.converter.Converter
import acsgh.slack.infrastucture.format.JsonFormat
import acsgh.slack.infrastucture.model._
import acsgh.slack.syntax._
import cats.effect.{ConcurrentEffect, Sync}
import cats.syntax.all._
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.headers.{`Content-Type`, _}
import org.http4s.{client, _}

import scala.concurrent.ExecutionContext

case class SlackClientHttp4s[F[_] : Sync : ConcurrentEffect : Logger]
(
  private val name: String,
  private val token: String,
  private val uri: Uri,
  private val jsonFormat: JsonFormat[F],
  private val converter: Converter[F]
)(implicit ex: ExecutionContext) extends SlackClient[F] {

  import jsonFormat._

  override def findUserByEmail(email: String): F[Option[User]] = {
    for {
      response <- urlFormRequest[UserResponse](
        "users.lookupByEmail",
        Map(
          "email" -> email
        ).toUrlForm
      )
      user <- response.user.mapF[User, F](converter.toDomain)
    } yield user
  }

  override def findUserById(id: String): F[Option[User]] = {
    for {
      response <- urlFormRequest[UserResponse](
        "users.info",
        Map(
          "user" -> id
        ).toUrlForm
      )
      user <- response.user.mapF[User, F](converter.toDomain)
    } yield user
  }

  override def getUserPresence(id: String): F[Option[UserPresence]] = {
    for {
      response <- urlFormRequest[FindUserPresenceResponse](
        "users.getPresence",
        Map(
          "user" -> id
        ).toUrlForm
      )
      user <- response.presence.flatMapF[UserPresence, F](converter.toDomain)
    } yield user
  }

  override def getAllUsers(nextCursor: Option[String], limit: Option[Int] ): F[Users] = {
    for {
      response <- urlFormRequest[UsersResponse](
        "users.list",
        Map(
          "cursor" -> nextCursor,
          "limit" -> limit
        ).toUrlForm
      )
      users <- converter.toDomain(response)
    } yield users
  }

  override def getAllConversations(nextCursor: Option[String], limit: Option[Int], excludeArchived: Boolean, types: Set[ConversationType]): F[Conversations] = {
    for {
      response <- urlFormRequest[ConversationsResponse](
        "conversations.list",
        Map(
          "cursor" -> nextCursor,
          "limit" -> limit,
          "exclude_archived" -> excludeArchived,
          "types" -> Option(types.map(_.entryName).mkString(",")).filter(_.nonEmpty)
        ).toUrlForm
      )
      users <- converter.toDomain(response)
    } yield users
  }

  override def getUserConversations(nextCursor: Option[String], limit: Option[Int], excludeArchived: Boolean, types: Set[ConversationType]): F[Conversations] = {
    for {
      response <- urlFormRequest[ConversationsResponse](
        "users.conversations",
        Map(
          "cursor" -> nextCursor,
          "limit" -> limit,
          "exclude_archived" -> excludeArchived,
          "types" -> Option(types.map(_.entryName).mkString(",")).filter(_.nonEmpty)
        ).toUrlForm
      )
      users <- converter.toDomain(response)
    } yield users
  }

  private def urlFormRequest[A <: SlackResponse](slackMethod: String, form: UrlForm)(implicit decoder: EntityDecoder[F, A]): F[A] = {
    val entity = UrlForm.entityEncoder[F].toEntity(form)
    val request = Request[F](
      uri = uri / slackMethod,
      method = Method.POST,
      headers = Headers.of(`Content-Type`(MediaType.application.`x-www-form-urlencoded`)),
      body = entity.body
    )
    for {
      response <- execute[A](request)
      _ <- logResponse(slackMethod, response)
    } yield response
  }


  private def logResponse(slackMethod: String, response: SlackResponse): F[Unit] = {
    import cats.implicits._
    for {
      _ <- response.error.fold(Sync[F].unit)(e => Logger[F].debug(s"Method $slackMethod reply the following error: $e"))
      _ <- response.warning.fold[F[_]](Sync[F].unit)(_.traverse(w => Logger[F].debug(s"Method $slackMethod reply the following warning: $w").attempt))
    } yield {}
  }

  private def execute[A](request: Request[F])(implicit decoder: EntityDecoder[F, A]): F[A] = {
    BlazeClientBuilder[F](ex).resource.use { rawClient =>
      val loggerClient = client.middleware.Logger(logHeaders = true, logBody = true)(rawClient)

      val newHeaders = request.headers.toList ++ List(
        `User-Agent`(AgentProduct(name)),
        Host(request.uri.host.map(_.value).getOrElse("")),
        Authorization(Credentials.Token(AuthScheme.Bearer, token))
      )

      val finalRequest = request.withHeaders(Headers(newHeaders))
      loggerClient.expect[A](finalRequest)
    }
  }
}
