package acsgh.slack

import cats.effect.Sync
import cats.syntax.all._

package object syntax {

  implicit class OptionOps[A](private val input: Option[A]) {
    def mapF[B, F[_] : Sync](action: A => F[B]): F[Option[B]] = input match {
      case Some(value) => action(value).map(Some[B])
      case None => Option.empty[B].pure[F]
    }

    def flatMapF[B, F[_] : Sync](action: A => F[Option[B]]): F[Option[B]] = input match {
      case Some(value) => action(value)
      case None => Option.empty[B].pure[F]
    }
  }
}
