package acsgh.slack.infrastucture.model

case class FindUserByEmailResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[List[String]],
  user:Option[User]
) extends SlackResponse
