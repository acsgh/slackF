package acsgh.slack.infrastucture

import cats.effect.Sync
import com.typesafe.scalalogging

object LoggerFactory {

  def sync[F[_] : Sync](clazz: Class[_]): Logger[F] = new Logger[F] {
    private val log = scalalogging.Logger(clazz)

    override def error(message: String): F[Unit] = Sync[F].delay(log.error(message))

    override def error(message: String, cause: Throwable): F[Unit] = Sync[F].delay(log.error(message, cause))

    override def error(message: String, args: Any*): F[Unit] = Sync[F].delay(log.error(message, args))

    override def warn(message: String): F[Unit] = Sync[F].delay(log.warn(message))

    override def warn(message: String, cause: Throwable): F[Unit] = Sync[F].delay(log.warn(message, cause))

    override def warn(message: String, args: Any*): F[Unit] = Sync[F].delay(log.warn(message, args))

    override def info(message: String): F[Unit] = Sync[F].delay(log.info(message))

    override def info(message: String, cause: Throwable): F[Unit] = Sync[F].delay(log.info(message, cause))

    override def info(message: String, args: Any*): F[Unit] = Sync[F].delay(log.info(message, args))

    override def debug(message: String): F[Unit] = Sync[F].delay(log.debug(message))

    override def debug(message: String, cause: Throwable): F[Unit] = Sync[F].delay(log.debug(message, cause))

    override def debug(message: String, args: Any*): F[Unit] = Sync[F].delay(log.debug(message, args))

    override def trace(message: String): F[Unit] = Sync[F].delay(log.trace(message))

    override def trace(message: String, cause: Throwable): F[Unit] = Sync[F].delay(log.trace(message, cause))

    override def trace(message: String, args: Any*): F[Unit] = Sync[F].delay(log.trace(message, args))

  }
}
