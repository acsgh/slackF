package acsgh.slack.domain.model

case class Users
(
  members: List[User],
  nextCursor: Option[String]
)

case class User
(
  id: String,
  teamId: String,
  name: String,
  deleted: Boolean,
  color: String,
  realName: String,
  timezone: Option[String],
  timezoneLabel: Option[String],
  timezoneOffset: Long,
  profile: Profile,
  isAdmin: Boolean,
  isOwner: Boolean,
  isPrimaryOwner: Boolean,
  isRestricted: Boolean,
  isUltraRestricted: Boolean,
  isBot: Boolean,
  updated: Long,
  isAppUser: Boolean,
)

case class Profile
(
  avatarHash: String,
  statusText: String,
  statusEmoji: String,
  realName: String,
  displayName: String,
  realNameNormalized: String,
  displayNameNormalized: String,
  email: Option[String],
  image24: String,
  image32: String,
  image48: String,
  image72: String,
  image192: String,
  image512: String,
  team: String
)
