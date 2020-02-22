package acsgh.slack.examples

import acsgh.slack.SlackClientBuilder
import cats.effect.{ConcurrentEffect, ExitCode, IO, IOApp, Sync}
import cats.implicits._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    runExample[IO]()
  }

  def runExample[F[_] : Sync : ConcurrentEffect](): F[ExitCode] = {
    implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
    for {
      token <- getToken
      client <- SlackClientBuilder.build[F](token)
      user <- client.getUserPresence("asd")
//      user <- client.findUserByEmail("asd@gmail.com")
      _ <- printValue(user)
    } yield ExitCode.Success
  }

  private def getToken[F[_] : Sync]: F[String] = sys.env.get("token").liftTo[F](new IllegalArgumentException("token env var not found"))

  private def printValue[F[_] : Sync](value: Any): F[Unit] = Sync[F].delay(println("--> + " + value))
}
