package acsgh.slack.domain.model

import enumeratum.EnumEntry.Snakecase
import enumeratum._

case class Conversations
(
  items: List[Conversation],
  nextCursor: Option[String]
)

sealed trait ConversationType extends EnumEntry with Snakecase

object ConversationType extends Enum[ConversationType] {

  val values = findValues

  case object PublicChannel extends ConversationType

  case object PrivateChannel extends ConversationType

  case object MPIM extends ConversationType

  case object IM extends ConversationType

  case object Away extends ConversationType

}

case class Conversation
(
  id: String,
  name: String,
  isChannel: Boolean,
  isGroup: Boolean,
  isIm: Boolean,
  created: Long,
  creator: String,
  isArchived: Boolean,
  isGeneral: Boolean,
  unlinked: Long,
  nameNormalized: String,
  isShared: Boolean,
  isExtShared: Boolean,
  isOrgShared: Boolean,
  pendingShared: List[String],
  isPendingExtShared: Boolean,
  isMember: Boolean,
  isPrivate: Boolean,
  isMpim: Boolean,
  topic: ConversationTopic,
  purpose: ConversationPurpose,
  previousNames: List[String],
  numMembers: Long,
)

case class ConversationTopic
(
  value: String,
  creator: String,
  lastSet: Long
)

case class ConversationPurpose
(
  value: String,
  creator: String,
  lastSet: Long
)
