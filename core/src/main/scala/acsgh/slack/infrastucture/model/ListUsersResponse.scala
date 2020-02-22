package acsgh.slack.infrastucture.model

case class ListUsersResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[List[String]],
  members: Option[List[User]],
  cache_ts: Long,
  response_metadata: ListUsersResponseMetadata
) extends SlackResponse

case class ListUsersResponseMetadata
(
  next_cursor: Option[String]
)
