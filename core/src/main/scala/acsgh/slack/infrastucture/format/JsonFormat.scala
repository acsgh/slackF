package acsgh.slack.infrastucture.format

import acsgh.slack.infrastucture.model.{FindUserByEmailResponse, FindUserByIdResponse, FindUserPresenceResponse}
import cats.effect.Sync
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

class JsonFormat[F[_] : Sync] {

  implicit val findUserByEmailResponseFormat: EntityDecoder[F, FindUserByEmailResponse] = jsonOf[F, FindUserByEmailResponse]

  implicit val findUserByIdResponseFormat: EntityDecoder[F, FindUserByIdResponse] = jsonOf[F, FindUserByIdResponse]

  implicit val findUserPresenceResponseFormat: EntityDecoder[F, FindUserPresenceResponse] = jsonOf[F, FindUserPresenceResponse]
}
