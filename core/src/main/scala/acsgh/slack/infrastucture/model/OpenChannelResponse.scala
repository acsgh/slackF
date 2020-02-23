package acsgh.slack.infrastucture.model

private[infrastucture] case class OpenChannelResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String],
  channel: Option[ConversationId],
) extends SlackResponse
