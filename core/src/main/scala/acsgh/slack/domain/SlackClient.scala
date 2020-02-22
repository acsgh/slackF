package acsgh.slack.domain

import acsgh.slack.domain.model.{User, UserPresence}

trait SlackClient[F[_]] {
  def findUserByEmail(email: String): F[Option[User]]

  def findUserById(id: String): F[Option[User]]

  def getUserPresence(id: String): F[Option[UserPresence]]
}
