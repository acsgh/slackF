package acsgh.slack.infrastucture.model

trait SlackResponse {
  val ok: Boolean
  val error: Option[String]
  val warning: Option[List[String]]
}
