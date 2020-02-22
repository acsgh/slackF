package acsgh.slack

import acsgh.slack.domain.SlackClient
import acsgh.slack.infrastucture.converter.Converter
import acsgh.slack.infrastucture.format.JsonFormat
import acsgh.slack.infrastucture.{Logger, LoggerFactory, SlackClientHttp4s}
import cats.effect.ConcurrentEffect
import cats.implicits._
import org.http4s.Uri

import scala.concurrent.ExecutionContext


object SlackClientBuilder {
  def build[F[_] : ConcurrentEffect]
  (
    token: String,
    uri: Uri = Uri.unsafeFromString("https://slack.com/api/")
  )(implicit ex: ExecutionContext): F[SlackClient[F]] = {
    implicit val logger: Logger[F] = LoggerFactory.sync(classOf[SlackClientHttp4s[F]])
    SlackClientHttp4s[F](
      "slackF",
      token,
      uri,
      new JsonFormat[F],
      new Converter[F]
    ).pure[F].map(_.asInstanceOf[SlackClient[F]])
  }
}