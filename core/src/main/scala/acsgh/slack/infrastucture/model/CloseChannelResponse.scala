package acsgh.slack.infrastucture.model

private[infrastucture] case class CloseChannelResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String],
  no_op: Option[Boolean],
  already_closed: Option[Boolean]
) extends SlackResponse
