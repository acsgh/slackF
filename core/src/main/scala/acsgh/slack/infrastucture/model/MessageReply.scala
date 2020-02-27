package acsgh.slack.infrastucture.model

private[infrastucture] case class MessageReply2
(
  ts: String,
  `type`: String,
  user: String,
  text: String,
  thread_ts: Option[String],
  reply_count: Long,
  replies: List[MessageRepliesSummary],
  subscribed: Boolean,
  last_read: String,
  unread_count: Long
)

private[infrastucture] case class MessageRepliesSummary
(
  ts: String,
  user: String
)

//"type": "message",
//"user": "U061F7AUR",
//"text": "island",
//"thread_ts": "1482960137.003543",
//"reply_count": 3,
//"replies": [
//{
//"user": "U061F7AUR",
//"ts": "1483037603.017503"
//},
//{
//"user": "U061F7AUR",
//"ts": "1483051909.018632"
//},
//{
//"user": "U061F7AUR",
//"ts": "1483125339.020269"
//}
//],
//"subscribed": true,
//"last_read": "1484678597.521003",
//"unread_count": 0,
//"ts": "1482960137.003543"