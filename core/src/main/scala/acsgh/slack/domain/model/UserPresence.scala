package acsgh.slack.domain.model

import enumeratum._

sealed trait UserPresence extends EnumEntry

object UserPresence extends Enum[UserPresence] {

  val values = findValues

  case object Active extends UserPresence

  case object Away extends UserPresence

}
