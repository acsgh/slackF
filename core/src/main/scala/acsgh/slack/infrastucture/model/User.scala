package acsgh.slack.infrastucture.model

private[infrastucture] case class User
(
  id: String,
  team_id: String,
  name: String,
  deleted: Boolean,
  color: String,
  real_name: String,
  tz: Option[String],
  tz_label: Option[String],
  tz_offset: Long,
  profile: Profile,
  is_admin: Boolean,
  is_owner: Boolean,
  is_primary_owner: Boolean,
  is_restricted: Boolean,
  is_ultra_restricted: Boolean,
  is_bot: Boolean,
  updated: Long,
  is_app_user: Boolean,
)

private[infrastucture] case class Profile
(
  avatar_hash: String,
  status_text: String,
  status_emoji: String,
  real_name: String,
  display_name: String,
  real_name_normalized: String,
  display_name_normalized: String,
  email: Option[String],
  image_24: String,
  image_32: String,
  image_48: String,
  image_72: String,
  image_192: String,
  image_512: String,
  team: String
)
