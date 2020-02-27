package acsgh.slack.infrastucture.model

private[infrastucture] case class MessagesResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String],
  messages: Option[List[Message]],
  response_metadata: Option[ResponseMetadata]
) extends SlackResponse

