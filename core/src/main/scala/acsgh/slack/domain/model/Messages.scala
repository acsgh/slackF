package acsgh.slack.domain.model

import enumeratum.EnumEntry.Snakecase
import enumeratum._

case class Messages
(
  items: List[Message],
  nextCursor: Option[String]
)

sealed trait MessageType extends EnumEntry with Snakecase

object MessageType extends Enum[MessageType] {

  val values = findValues

  case object Message extends MessageType

  case class Unknown(override val entryName: String) extends MessageType

  override def withNameOption(name: String): Option[MessageType] = namesToValuesMap.get(name).orElse(Some(Unknown(name)))

}

sealed trait MessageSubtype extends EnumEntry with Snakecase

object MessageSubtype extends Enum[MessageSubtype] {

  val values = findValues

  case object ThreadBroadcast extends MessageSubtype

  case class Unknown(override val entryName: String) extends MessageSubtype

  override def withNameOption(name: String): Option[MessageSubtype] = namesToValuesMap.get(name).orElse(Some(Unknown(name)))
}


case class Message
(
  id: String,
  `type`: MessageType,
  subtype: Option[MessageSubtype],
  user: String,
  text: String,
  threadId: Option[String],
  replyCount: Long,
  subscribed: Boolean,
  blocks: List[MessageBlock],
  attachments: List[MessageAttachment]
) {
  def isThreadRootMessage: Boolean = threadId.isEmpty || threadId.contains(id)
}

sealed trait MessageBlockType extends EnumEntry with Snakecase

object MessageBlockType extends Enum[MessageBlockType] {

  val values = findValues

  case object RichText extends MessageBlockType

  case class Unknown(override val entryName: String) extends MessageBlockType

  override def withNameOption(name: String): Option[MessageBlockType] = namesToValuesMap.get(name).orElse(Some(Unknown(name)))
}

case class MessageBlock
(
  id: String,
  `type`: MessageBlockType,
  elements: List[MessageBlockElement]
)

sealed trait MessageBlockElementType extends EnumEntry with Snakecase

object MessageBlockElementType extends Enum[MessageBlockElementType] {

  val values = findValues

  case object RichTextSection extends MessageBlockElementType

  case class Unknown(override val entryName: String) extends MessageBlockElementType

  override def withNameOption(name: String): Option[MessageBlockElementType] = namesToValuesMap.get(name).orElse(Some(Unknown(name)))
}

case class MessageBlockElement
(
  `type`: MessageBlockElementType,
  elements: List[MessageBlockElementChild]
)

sealed trait MessageBlockElementChildType extends EnumEntry with Snakecase

object MessageBlockElementChildType extends Enum[MessageBlockElementChildType] {

  val values = findValues

  case object Text extends MessageBlockElementChildType

  case class Unknown(override val entryName: String) extends MessageBlockElementChildType

  override def withNameOption(name: String): Option[MessageBlockElementChildType] = namesToValuesMap.get(name).orElse(Some(Unknown(name)))
}

case class MessageBlockElementChild
(
  `type`: MessageBlockElementChildType,
  text: String
)

case class MessageAttachment
(
  id: Long,
  serviceName: String,
  text: String,
  fallback: String,
  thumbUrl: String,
  thumbWidth: Long,
  thumbHeight: Long
)