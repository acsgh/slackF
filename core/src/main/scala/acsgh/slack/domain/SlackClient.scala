package acsgh.slack.domain

import acsgh.slack.domain.model._

trait SlackClient[F[_]] {
  def findUserByEmail(email: String): F[Option[User]]

  def findUserById(id: String): F[Option[User]]

  def getUserPresence(id: String): F[Option[UserPresence]]

  def getAllUsers(nextCursor: Option[String] = None, limit: Option[Int] = None): F[Users]

  def getAllConversations(nextCursor: Option[String] = None, limit: Option[Int] = None, excludeArchived: Boolean = true, types: Set[ConversationType] = Set(ConversationType.PublicChannel)): F[Conversations]

  def getUserConversations(nextCursor: Option[String] = None, limit: Option[Int] = None, excludeArchived: Boolean = true, types: Set[ConversationType] = Set(ConversationType.PublicChannel)): F[Conversations]
}
