package acsgh.slack.infrastucture.model

private[infrastucture] case class UserResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[String],
  user: Option[User]
) extends SlackResponse
