package acsgh.slack.domain.model

case class User
(
  id: String,
  teamId: String,
  name: String,
  deleted: Boolean,
  color: String,
  realName: String,
  timezone: String,
  timezoneLabel: String,
  timezoneOffset: Int,
  profile:Profile,
  isAdmin: Boolean,
  isOwner: Boolean,
  isPrimaryOwner: Boolean,
  isRestricted: Boolean,
  isUltraRestricted: Boolean,
  isBot: Boolean,
  updated: Int,
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
  email: String,
  image24: String,
  image32: String,
  image48: String,
  image72: String,
  image192: String,
  image512: String,
  team: String
)
