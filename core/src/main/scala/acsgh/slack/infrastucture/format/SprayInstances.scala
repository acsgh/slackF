package acsgh.slack.infrastucture.format

import cats.data.EitherT
import cats.effect.Sync
import cats.implicits._
import org.http4s._
import spray.json._

trait SprayInstances extends DefaultJsonProtocol {

  def decoderOf[F[_] : Sync, A](implicit format: JsonFormat[A]): EntityDecoder[F, A] = decoderOfWithMedia(MediaType.application.json)

  def decoderOfWithMedia[F[_] : Sync, A](r1: MediaRange, rs: MediaRange*)(implicit format: JsonFormat[A]): EntityDecoder[F, A] =
    EntityDecoder.decodeBy(r1, rs: _*) { m: Media[F] =>
      EitherT {
        m.as[String].map(s => s.parseJson.convertTo[A].asRight[DecodeFailure])
      }
    }
}

