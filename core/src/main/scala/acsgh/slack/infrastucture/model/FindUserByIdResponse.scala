package acsgh.slack.infrastucture.model

case class FindUserByIdResponse
(
  ok: Boolean,
  error: Option[String],
  warning: Option[List[String]],
  user:Option[User]
) extends SlackResponse
