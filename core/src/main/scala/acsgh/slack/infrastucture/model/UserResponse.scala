package acsgh.slack.infrastucture.model

private[infrastucture]  case class UserResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[List[String]],
  user:Option[User]
) extends SlackResponse
