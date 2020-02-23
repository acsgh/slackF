package acsgh.slack.infrastucture.model

private[infrastucture] trait SlackResponse {
  val ok: Boolean
  val error: Option[String]
  val warning: Option[String]
}

private[infrastucture] case class BasicSlackResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String]
) extends SlackResponse

private[infrastucture] case class ResponseMetadata
(
  next_cursor: Option[String]
)