package acsgh.slack.infrastucture.converter

import acsgh.slack.domain.model._
import acsgh.slack.infrastucture.model.ListUsersResponse
import acsgh.slack.infrastucture.{model => infrastucture}
import cats.effect.Sync
import cats.implicits._

class Converter[F[_] : Sync] {


  def toDomain(input: ListUsersResponse): F[Users] = {
    for {
      members <- input.members.fold(List.empty[User].pure[F])(_.traverse(toDomain))
    } yield Users(
      members = members,
      nextCursor = input.response_metadata.next_cursor.filter(_.nonEmpty)
    )
  }

  def toDomain(input: String): F[Option[UserPresence]] = UserPresence.withNameInsensitiveOption(input).pure[F]

  def toDomain(input: infrastucture.User): F[User] = User(
    id = input.id,
    teamId = input.team_id,
    name = input.name,
    deleted = input.deleted,
    color = input.color,
    realName = input.real_name,
    timezone = input.tz,
    timezoneLabel = input.tz_label,
    timezoneOffset = input.tz_offset,
    profile = toDomain(input.profile),
    isAdmin = input.is_admin,
    isOwner = input.is_owner,
    isPrimaryOwner = input.is_primary_owner,
    isRestricted = input.is_restricted,
    isUltraRestricted = input.is_ultra_restricted,
    isBot = input.is_bot,
    updated = input.updated,
    isAppUser = input.is_app_user,
  ).pure[F]

  private def toDomain(input: infrastucture.Profile): Profile = Profile(
    avatarHash = input.avatar_hash,
    statusText = input.status_text,
    statusEmoji = input.status_emoji,
    realName = input.real_name,
    displayName = input.display_name,
    realNameNormalized = input.real_name_normalized,
    displayNameNormalized = input.display_name_normalized,
    email = input.email,
    image24 = input.image_24,
    image32 = input.image_32,
    image48 = input.image_48,
    image72 = input.image_72,
    image192 = input.image_192,
    image512 = input.image_512,
    team = input.team,
  )
}
