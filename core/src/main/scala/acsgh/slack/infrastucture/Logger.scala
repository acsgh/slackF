package acsgh.slack.infrastucture

object Logger {
  def apply[F[_]](implicit instance: Logger[F]): Logger[F] = instance
}

trait Logger[F[_]] {

  def error(message: String): F[Unit]

  def error(message: String, cause: Throwable): F[Unit]

  def error(message: String, args: Any*): F[Unit]

  def warn(message: String): F[Unit]

  def warn(message: String, cause: Throwable): F[Unit]

  def warn(message: String, args: Any*): F[Unit]

  def info(message: String): F[Unit]

  def info(message: String, cause: Throwable): F[Unit]

  def info(message: String, args: Any*): F[Unit]

  def debug(message: String): F[Unit]

  def debug(message: String, cause: Throwable): F[Unit]

  def debug(message: String, args: Any*): F[Unit]

  def trace(message: String): F[Unit]

  def trace(message: String, cause: Throwable): F[Unit]

  def trace(message: String, args: Any*): F[Unit]
}
