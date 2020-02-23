package acsgh.slack.infrastucture.model

private[infrastucture] case class Conversation
(
  id: String,
  name: String,
  is_channel: Boolean,
  is_group: Boolean,
  is_im: Boolean,
  created: Long,
  creator: String,
  is_archived: Boolean,
  is_general: Boolean,
  unlinked: Long,
  name_normalized: String,
  is_shared: Boolean,
  is_ext_shared: Boolean,
  is_org_shared: Boolean,
  pending_shared: List[String],
  is_pending_ext_shared: Boolean,
  is_member: Boolean,
  is_private: Boolean,
  is_mpim: Boolean,
  topic: ConversationTopic,
  purpose: ConversationPurpose,
  previous_names: List[String],
  num_members: Long,
)

private[infrastucture] case class ConversationTopic
(
  value: String,
  creator: String,
  last_set: Long
)

private[infrastucture] case class ConversationPurpose
(
  value: String,
  creator: String,
  last_set: Long
)