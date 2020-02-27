package acsgh.slack.infrastucture.model

private[infrastucture] case class Message
(
  ts: String,
  `type`: String,
  subtype: Option[String],
  user: String,
  text: String,
  thread_ts: Option[String],
  blocks: Option[List[MessageBlock]],
  reply_count: Option[Long],
  reply_users_count: Option[Long],
  latest_reply: Option[String],
  reply_users: Option[List[String]],
  replies: Option[List[MessageReply]],
  subscribed: Option[Boolean],
  attachments: Option[List[MessageAttachment]]

)

private[infrastucture] case class MessageAttachment
(
  id: Long,
  service_name: String,
  text: String,
  fallback: String,
  thumb_url: String,
  thumb_width: Long,
  thumb_height: Long
)

private[infrastucture] case class MessageBlock
(
  `type`: String,
  block_id: String,
  elements: Option[List[MessageBlockElement]]
)

private[infrastucture] case class MessageBlockElement
(
  `type`: String,
  elements: List[MessageBlockElementChild]
)

private[infrastucture] case class MessageBlockElementChild
(
  `type`: String,
  text: String
)

private[infrastucture] case class MessageReply
(
  user: String,
  ts: String
)