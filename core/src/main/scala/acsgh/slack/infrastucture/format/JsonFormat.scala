package acsgh.slack.infrastucture.format

import acsgh.slack.infrastucture.model._
import cats.effect.Sync
import org.http4s.EntityDecoder
import spray.json._

class JsonFormat[F[_] : Sync] extends SprayInstances {

  private implicit val profileFormat: RootJsonFormat[Profile] = jsonFormat15(Profile)

  private implicit val userFormat: RootJsonFormat[User] = jsonFormat18(User)

  private implicit val findUserByEmailResponseFormat: RootJsonFormat[FindUserByEmailResponse] = jsonFormat4(FindUserByEmailResponse)

  private implicit val findUserByIdResponseFormat: RootJsonFormat[FindUserByIdResponse] = jsonFormat4(FindUserByIdResponse)

  private implicit val listUsersResponseMetadataFormat: RootJsonFormat[ListUsersResponseMetadata] = jsonFormat1(ListUsersResponseMetadata)

  private implicit val listUsersResponseFormat: RootJsonFormat[ListUsersResponse] = jsonFormat6(ListUsersResponse)

  private implicit val findUserPresenceResponseFormat: RootJsonFormat[FindUserPresenceResponse] = jsonFormat4(FindUserPresenceResponse)

  implicit val findUserByEmailResponseEncoder: EntityDecoder[F, FindUserByEmailResponse] = decoderOf[F, FindUserByEmailResponse]

  implicit val findUserByIdResponseEncoder: EntityDecoder[F, FindUserByIdResponse] = decoderOf[F, FindUserByIdResponse]

  implicit val findUserPresenceResponseEncoder: EntityDecoder[F, FindUserPresenceResponse] = decoderOf[F, FindUserPresenceResponse]

  implicit val listUsersResponseEncoder: EntityDecoder[F, ListUsersResponse] = decoderOf[F, ListUsersResponse]
}
