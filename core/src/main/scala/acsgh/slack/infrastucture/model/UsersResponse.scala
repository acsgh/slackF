package acsgh.slack.infrastucture.model

private[infrastucture] case class UsersResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[List[String]],
  members: Option[List[User]],
  cache_ts: Long,
  response_metadata: Option[ResponseMetadata]
) extends SlackResponse