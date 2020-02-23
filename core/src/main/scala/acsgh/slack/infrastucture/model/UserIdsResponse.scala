package acsgh.slack.infrastucture.model

private[infrastucture] case class UserIdsResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String],
  members: Option[List[String]],
  response_metadata: Option[ResponseMetadata]
) extends SlackResponse