package acsgh.slack.infrastucture.model

private[infrastucture] case class ConversationsResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String],
  channels: Option[List[Conversation]],
  response_metadata: Option[ResponseMetadata]
) extends SlackResponse

