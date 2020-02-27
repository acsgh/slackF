package acsgh.slack.infrastucture.converter

import acsgh.slack.domain.model._
import acsgh.slack.infrastucture.model.{ConversationsResponse, MessagesResponse, UserIdsResponse, UsersResponse}
import acsgh.slack.infrastucture.{model => infrastucture}
import cats.effect.Sync
import cats.implicits._

class Converter[F[_] : Sync] {

  def toDomain(input: UserIdsResponse): F[UserIds] = UserIds(
    ids = input.members.getOrElse(List.empty),
    nextCursor = input.response_metadata.flatMap(_.next_cursor.filter(_.nonEmpty))
  ).pure[F]

  def toDomain(input: UsersResponse): F[Users] = {
    for {
      members <- input.members.fold(List.empty[User].pure[F])(_.traverse(toDomain))
    } yield Users(
      items = members,
      nextCursor = input.response_metadata.flatMap(_.next_cursor.filter(_.nonEmpty))
    )
  }

  def toDomain(input: MessagesResponse): F[Messages] = {
    for {
      members <- input.messages.fold(List.empty[Message].pure[F])(_.traverse(toDomain))
    } yield Messages(
      items = members,
      nextCursor = input.response_metadata.flatMap(_.next_cursor.filter(_.nonEmpty))
    )
  }

  def toDomain(input: ConversationsResponse): F[Conversations] = {
    for {
      channels <- input.channels.fold(List.empty[Conversation].pure[F])(_.traverse(toDomain))
    } yield Conversations(
      items = channels,
      nextCursor = input.response_metadata.flatMap(_.next_cursor.filter(_.nonEmpty))
    )
  }

  def toDomain(input: String): F[Option[UserPresence]] = UserPresence.withNameInsensitiveOption(input).pure[F]

  def toDomain(input: infrastucture.Message): F[Message] = {
    for {
      attachments <- input.attachments.fold(List.empty[MessageAttachment].pure[F])(_.traverse(toDomain))
      blocks <- input.blocks.fold(List.empty[MessageBlock].pure[F])(_.traverse(toDomain))
    } yield Message(
      id = input.ts,
      `type` = MessageType.withName(input.`type`),
      subtype = input.subtype.flatMap(MessageSubtype.withNameOption),
      user = input.user,
      text = input.text,
      threadId = input.thread_ts,
      replyCount = input.reply_count.getOrElse(0),
      subscribed = input.subscribed.getOrElse(false),
      blocks = blocks,
      attachments = attachments
    )
  }

  def toDomain(input: infrastucture.MessageBlock): F[MessageBlock] = {
    for {
      elements <- input.elements.fold(List.empty[MessageBlockElement].pure[F])(_.traverse(toDomain))
    } yield MessageBlock(
      id = input.block_id,
      `type` = MessageBlockType.withName(input.`type`),
      elements = elements
    )
  }

  def toDomain(input: infrastucture.MessageBlockElement): F[MessageBlockElement] = {
    for {
      elements <- input.elements.traverse(toDomain)
    } yield MessageBlockElement(
      `type` = MessageBlockElementType.withName(input.`type`),
      elements = elements
    )
  }

  def toDomain(input: infrastucture.MessageBlockElementChild): F[MessageBlockElementChild] = MessageBlockElementChild(
    `type` = MessageBlockElementChildType.withName(input.`type`),
    text = input.text
  ).pure[F]

  def toDomain(input: infrastucture.MessageAttachment): F[MessageAttachment] = MessageAttachment(
    id = input.id,
    serviceName = input.service_name,
    text = input.text,
    fallback = input.fallback,
    thumbUrl = input.thumb_url,
    thumbWidth = input.thumb_width,
    thumbHeight = input.thumb_height
  ).pure[F]

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

  def toDomain(input: infrastucture.Conversation): F[Conversation] = Conversation(
    id = input.id,
    name = input.name,
    isChannel = input.is_channel,
    isGroup = input.is_group,
    isIm = input.is_im,
    created = input.created,
    creator = input.creator,
    isArchived = input.is_archived,
    isGeneral = input.is_general,
    unlinked = input.unlinked,
    nameNormalized = input.name_normalized,
    isShared = input.is_shared,
    isExtShared = input.is_ext_shared,
    isOrgShared = input.is_org_shared,
    pendingShared = input.pending_shared,
    isPendingExtShared = input.is_pending_ext_shared,
    isMember = input.is_member,
    isPrivate = input.is_private,
    isMpim = input.is_mpim,
    topic = toDomain(input.topic),
    purpose = toDomain(input.purpose),
    previousNames = input.previous_names,
    numMembers = input.num_members
  ).pure[F]

  private def toDomain(input: infrastucture.ConversationTopic): ConversationTopic = ConversationTopic(
    value = input.value,
    creator = input.creator,
    lastSet = input.last_set
  )

  private def toDomain(input: infrastucture.ConversationPurpose): ConversationPurpose = ConversationPurpose(
    value = input.value,
    creator = input.creator,
    lastSet = input.last_set
  )

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
