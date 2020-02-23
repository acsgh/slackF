package acsgh.slack.domain

import acsgh.slack.domain.model._

trait SlackClient[F[_]] {
  def findUserByEmail(email: String): F[Option[User]]

  def findUserById(id: String): F[Option[User]]

  def getUserPresence(id: String): F[Option[UserPresence]]

  def getAllUsers(nextCursor: Option[String] = None, limit: Option[Int] = None): F[Users]

  def getAllConversations(nextCursor: Option[String] = None, limit: Option[Int] = None, excludeArchived: Boolean = true, types: Set[ConversationType] = Set(ConversationType.PublicChannel)): F[Conversations]

  def getUserConversations(nextCursor: Option[String] = None, limit: Option[Int] = None, excludeArchived: Boolean = true, types: Set[ConversationType] = Set(ConversationType.PublicChannel)): F[Conversations]

  def findConversation(id: String): F[Option[Conversation]]

  def joinConversation(id: String): F[Option[Conversation]]

  def leaveConversation(id: String): F[Boolean]

  def closeConversation(id: String): F[Boolean]

  def archiveConversation(id: String): F[Boolean]

  def unarchiveConversation(id: String): F[Boolean]

  def inviteConversation(id: String, userIds: List[String]): F[Boolean]

  def createConversation(name: String, `private`: Boolean, userIds: List[String] = List.empty): F[Option[Conversation]]

  def openUserConversation(userIds: List[String]): F[Option[String]]

  def openChannelConversation(channelId: String): F[Option[String]]

  def setConversationTopic(id:String, topic: String): F[Boolean]

  def setConversationPurpose(id:String, purpose: String): F[Boolean]

  def renameConversation(id:String, name: String): F[Boolean]

  def getConversationMembers(id:String, nextCursor: Option[String] = None, limit: Option[Int] = None): F[UserIds]
}
