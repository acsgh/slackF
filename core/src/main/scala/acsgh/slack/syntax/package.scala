package acsgh.slack

import cats.effect.Sync
import cats.syntax.all._
import org.http4s.UrlForm

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

  implicit class MapOps(private val input: Map[String, Any]) {
    def toUrlForm: UrlForm = UrlForm(
      input.filter(_._2 != None)
        .view
        .mapValues {
          case Some(v) => v.toString
          case v => v.toString
        }
        .toList
        : _*
    )
  }

}
