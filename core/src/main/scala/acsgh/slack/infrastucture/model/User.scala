package acsgh.slack.infrastucture.model

case class User
(
  id: String,
  team_id: String,
  name: String,
  deleted: Boolean,
  color: String,
  real_name: String,
  tz: String,
  tz_label: String,
  tz_offset: Int,
  profile:Profile,
  is_admin: Boolean,
  is_owner: Boolean,
  is_primary_owner: Boolean,
  is_restricted: Boolean,
  is_ultra_restricted: Boolean,
  is_bot: Boolean,
  updated: Int,
  is_app_user: Boolean,
)

case class Profile
(
  avatar_hash: String,
  status_text: String,
  status_emoji: String,
  real_name: String,
  display_name: String,
  real_name_normalized: String,
  display_name_normalized: String,
  email: String,
  image_24: String,
  image_32: String,
  image_48: String,
  image_72: String,
  image_192: String,
  image_512: String,
  team: String
)
