package acsgh.slack.infrastucture.model

private[infrastucture] case class ConversationResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String],
  channel: Option[Conversation]
) extends SlackResponse
