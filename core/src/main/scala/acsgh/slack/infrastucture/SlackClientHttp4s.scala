package acsgh.slack.infrastucture

import acsgh.slack.domain.SlackClient
import acsgh.slack.domain.model._
import acsgh.slack.infrastucture.converter.Converter
import acsgh.slack.infrastucture.format.SlackJsonFormat
import acsgh.slack.infrastucture.model.{Conversation => _, User => _, _}
import acsgh.slack.syntax._
import cats.effect.{ConcurrentEffect, Sync}
import cats.syntax.all._
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.headers.{`Content-Type`, _}
import org.http4s.{client, _}
import spray.json.JsonFormat

import scala.concurrent.ExecutionContext

case class SlackClientHttp4s[F[_] : Sync : ConcurrentEffect : Logger]
(
  private val name: String,
  private val token: String,
  private val uri: Uri,
  private val jsonFormat: SlackJsonFormat[F],
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

  override def getAllUsers(nextCursor: Option[String], limit: Option[Int]): F[Users] = {
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

  override def findConversation(id: String): F[Option[Conversation]] = {
    for {
      response <- urlFormRequest[ConversationResponse](
        "conversations.info",
        Map(
          "channel" -> id
        ).toUrlForm
      )
      conversation <- response.channel.mapF[Conversation, F](converter.toDomain)
    } yield conversation
  }

  override def joinConversation(id: String): F[Option[Conversation]] = {
    for {
      response <- urlFormRequest[ConversationResponse](
        "conversations.join",
        Map(
          "channel" -> id
        ).toUrlForm
      )
      conversation <- response.channel.mapF[Conversation, F](converter.toDomain)
    } yield conversation
  }

  override def leaveConversation(id: String): F[Boolean] = {
    for {
      response <- urlFormRequest[LeaveChannelResponse](
        "conversations.leave",
        Map(
          "channel" -> id
        ).toUrlForm
      )
    } yield !response.not_in_channel.contains(true) && response.error.isEmpty
  }

  override def closeConversation(id: String): F[Boolean] = {
    for {
      response <- urlFormRequest[CloseChannelResponse](
        "conversations.close",
        Map(
          "channel" -> id
        ).toUrlForm
      )
    } yield !response.no_op.contains(true) && !response.already_closed.contains(true) && response.error.isEmpty
  }

  override def archiveConversation(id: String): F[Boolean] = {
    for {
      response <- urlFormRequest[BasicSlackResponse](
        "conversations.archive",
        Map(
          "channel" -> id
        ).toUrlForm
      )
    } yield response.ok
  }

  override def unarchiveConversation(id: String): F[Boolean] = {
    for {
      response <- urlFormRequest[BasicSlackResponse](
        "conversations.unarchive",
        Map(
          "channel" -> id
        ).toUrlForm
      )
    } yield response.ok
  }

  override def inviteConversation(id: String, userIds: List[String]): F[Boolean] = {
    for {
      response <- urlFormRequest[BasicSlackResponse](
        "conversations.invite",
        Map(
          "channel" -> id,
          "users" -> userIds.mkString(",")
        ).toUrlForm
      )
    } yield response.ok
  }


  override def createConversation(name: String, `private`: Boolean, userIds: List[String]): F[Option[Conversation]] = {
    for {
      response <- urlFormRequest[ConversationResponse](
        "conversations.create",
        Map(
          "name" -> name,
          "is_private" -> `private`,
          "users_ids" -> Option(userIds.mkString(",")).filter(_.nonEmpty)
        ).toUrlForm
      )
      conversation <- response.channel.mapF[Conversation, F](converter.toDomain)
    } yield conversation
  }


  override def openUserConversation(userIds: List[String]): F[Option[String]] = {
    for {
      response <- urlFormRequest[OpenChannelResponse](
        "conversations.open",
        Map(
          "name" -> name,
          "users" -> Option(userIds.mkString(",")).filter(_.nonEmpty)
        ).toUrlForm
      )
    } yield response.channel.map(_.id)
  }

  override def openChannelConversation(channelId: String): F[Option[String]] = {
    for {
      response <- urlFormRequest[OpenChannelResponse](
        "conversations.open",
        Map(
          "name" -> name,
          "channel" -> channelId
        ).toUrlForm
      )
    } yield response.channel.map(_.id)
  }

  override def setConversationTopic(id:String, topic: String): F[Boolean] = {
    for {
      response <- urlFormRequest[BasicSlackResponse](
        "conversations.setTopic",
        Map(
          "channel" -> id,
          topic -> topic
        ).toUrlForm
      )
    } yield response.ok
  }

  override def setConversationPurpose(id:String, purpose: String): F[Boolean] = {
    for {
      response <- urlFormRequest[BasicSlackResponse](
        "conversations.setPurpose",
        Map(
          "channel" -> id,
          purpose -> purpose
        ).toUrlForm
      )
    } yield response.ok
  }

  override def renameConversation(id:String, name: String): F[Boolean] = {
    for {
      response <- urlFormRequest[BasicSlackResponse](
        "conversations.rename",
        Map(
          "channel" -> id,
          name -> name
        ).toUrlForm
      )
    } yield response.ok
  }


  override def getConversationMembers(id: String, nextCursor: Option[String], limit: Option[Int]): F[UserIds] = {
    for {
      response <- urlFormRequest[UserIdsResponse](
        "conversations.members",
        Map(
          "channel" -> id,
          "cursor" -> nextCursor,
          "limit" -> limit
        ).toUrlForm
      )
      users <- converter.toDomain(response)
    } yield users
  }

  private def urlFormRequest[A <: SlackResponse](slackMethod: String, form: UrlForm)(implicit format: JsonFormat[A]): F[A] = {
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
      _ <- response.warning.fold(Sync[F].unit)(e => Logger[F].debug(s"Method $slackMethod reply the following warning: $e"))
    } yield {}
  }

  private def execute[A](request: Request[F])(implicit format: JsonFormat[A]): F[A] = {
    BlazeClientBuilder[F](ex).resource.use { rawClient =>
      val loggerClient = client.middleware.Logger(logHeaders = true, logBody = true)(rawClient)

      val newHeaders = request.headers.toList ++ List(
        `User-Agent`(AgentProduct(name)),
        Host(request.uri.host.map(_.value).getOrElse("")),
        Authorization(Credentials.Token(AuthScheme.Bearer, token))
      )
      implicit val encoder: EntityDecoder[F, A] = jsonFormat.decoderOf[F, A]
      val finalRequest = request.withHeaders(Headers(newHeaders))
      loggerClient.expect[A](finalRequest)
    }
  }
}
