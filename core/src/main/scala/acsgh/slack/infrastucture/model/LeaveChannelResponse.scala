package acsgh.slack.infrastucture.model

private[infrastucture]  case class LeaveChannelResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String],
  not_in_channel:Option[Boolean]
) extends SlackResponse
