package acsgh.slack.infrastucture.model

private[infrastucture] case class FindUserPresenceResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[List[String]],
  presence: Option[String]
) extends SlackResponse
