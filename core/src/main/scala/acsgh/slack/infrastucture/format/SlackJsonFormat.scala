package acsgh.slack.infrastucture.format

import acsgh.slack.infrastucture.model._
import cats.effect.Sync
import spray.json._

class SlackJsonFormat[F[_] : Sync] extends SprayInstances {

  implicit val conversationIdFormat: RootJsonFormat[ConversationId] = jsonFormat1(ConversationId)

  implicit val conversationPurposeFormat: RootJsonFormat[ConversationPurpose] = jsonFormat3(ConversationPurpose)

  implicit val conversationTopicFormat: RootJsonFormat[ConversationTopic] = jsonFormat3(ConversationTopic)

  implicit object ConversationFormat extends RootJsonFormat[Conversation] {
    def write(c: Conversation): JsObject = JsObject(
      "id" -> JsString(c.id),
      "name" -> JsString(c.name),
      "is_channel" -> JsBoolean(c.is_channel),
      "is_group" -> JsBoolean(c.is_group),
      "is_im" -> JsBoolean(c.is_im),
      "created" -> JsNumber(c.created),
      "creator" -> JsString(c.creator),
      "is_archived" -> JsBoolean(c.is_archived),
      "is_general" -> JsBoolean(c.is_general),
      "unlinked" -> JsNumber(c.unlinked),
      "name_normalized" -> JsString(c.name_normalized),
      "is_shared" -> JsBoolean(c.is_shared),
      "is_ext_shared" -> JsBoolean(c.is_ext_shared),
      "is_org_shared" -> JsBoolean(c.is_org_shared),
      "pending_shared" -> JsArray(c.pending_shared.map(JsString(_)).toVector),
      "is_pending_ext_shared" -> JsBoolean(c.is_pending_ext_shared),
      "is_member" -> JsBoolean(c.is_member),
      "is_private" -> JsBoolean(c.is_private),
      "is_mpim" -> JsBoolean(c.is_mpim),
      "topic" -> conversationTopicFormat.write(c.topic),
      "purpose" -> conversationPurposeFormat.write(c.purpose),
      "previous_names" -> JsArray(c.previous_names.map(JsString(_)).toVector),
      "num_members" -> c.num_members.fold[JsValue](JsNull)(JsNumber(_))
    )

    def read(value: JsValue): Conversation = value match {
      case JsObject(fields) =>
        Conversation(
          id = fields("id").asInstanceOf[JsString].value,
          name = fields("name").asInstanceOf[JsString].value,
          is_channel = fields("is_channel").asInstanceOf[JsBoolean].value,
          is_group = fields("is_group").asInstanceOf[JsBoolean].value,
          is_im = fields("is_im").asInstanceOf[JsBoolean].value,
          created = fields("created").asInstanceOf[JsNumber].value.toLong,
          creator = fields("creator").asInstanceOf[JsString].value,
          is_archived = fields("is_archived").asInstanceOf[JsBoolean].value,
          is_general = fields("is_general").asInstanceOf[JsBoolean].value,
          unlinked = fields("unlinked").asInstanceOf[JsNumber].value.toLong,
          name_normalized = fields("name_normalized").asInstanceOf[JsString].value,
          is_shared = fields("is_shared").asInstanceOf[JsBoolean].value,
          is_ext_shared = fields("is_ext_shared").asInstanceOf[JsBoolean].value,
          is_org_shared = fields("is_org_shared").asInstanceOf[JsBoolean].value,
          pending_shared = toList(fields("pending_shared").asInstanceOf[JsArray]),
          is_pending_ext_shared = fields("is_pending_ext_shared").asInstanceOf[JsBoolean].value,
          is_member = fields.get("is_member").forall(_.asInstanceOf[JsBoolean].value),
          is_private = fields("is_private").asInstanceOf[JsBoolean].value,
          is_mpim = fields("is_mpim").asInstanceOf[JsBoolean].value,
          topic = conversationTopicFormat.read(fields("topic").asInstanceOf[JsObject]),
          purpose = conversationPurposeFormat.read(fields("purpose").asInstanceOf[JsObject]),
          previous_names = toList(fields("previous_names").asInstanceOf[JsArray]),
          num_members = fields.get("num_members").map(_.asInstanceOf[JsNumber].value.toLong)
        )
      case _ => deserializationError("Conversation expected")
    }
  }

  implicit val profileFormat: RootJsonFormat[Profile] = jsonFormat15(Profile)

  implicit val userFormat: RootJsonFormat[User] = jsonFormat18(User)

  implicit val userResponseFormat: RootJsonFormat[UserResponse] = jsonFormat4(UserResponse)

  implicit val responseMetadataFormat: RootJsonFormat[ResponseMetadata] = jsonFormat1(ResponseMetadata)

  implicit val userPresenceFormat: RootJsonFormat[FindUserPresenceResponse] = jsonFormat4(FindUserPresenceResponse)

  implicit val usersFormat: RootJsonFormat[UsersResponse] = jsonFormat6(UsersResponse)

  implicit val conversationsFormat: RootJsonFormat[ConversationsResponse] = jsonFormat5(ConversationsResponse)

  implicit val conversationFormat: RootJsonFormat[ConversationResponse] = jsonFormat4(ConversationResponse)

  implicit val leaveChannelResponseFormat: RootJsonFormat[LeaveChannelResponse] = jsonFormat4(LeaveChannelResponse)

  implicit val closeChannelResponseFormat: RootJsonFormat[CloseChannelResponse] = jsonFormat5(CloseChannelResponse)

  implicit val basicSlackResponseFormat: RootJsonFormat[BasicSlackResponse] = jsonFormat3(BasicSlackResponse)

  implicit val openChannelResponseFormat: RootJsonFormat[OpenChannelResponse] = jsonFormat4(OpenChannelResponse)

  implicit val userIdsResponseFormat: RootJsonFormat[UserIdsResponse] = jsonFormat5(UserIdsResponse)

  def toList(input: JsArray): List[String] = input.elements.map(_.asInstanceOf[JsString].value).toList
}
