package acsgh.slack.infrastucture.model

private[infrastucture] trait SlackResponse {
  val ok: Boolean
  val error: Option[String]
  val warning: Option[List[String]]
}

private[infrastucture] case class ResponseMetadata
(
  next_cursor: Option[String]
)