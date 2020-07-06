package net.mcarolan.amqpscodec

import scodec.bits.BitVector
import scodec.{Attempt, Codec, Encoder}
import scodec.codecs._

object AmqpTypes {

  sealed trait AmqpType

  case class ShortString(value: String) extends AmqpType

  object ShortString {
    val codec: Codec[ShortString] = variableSizeBits(int32, utf8).xmap(ShortString.apply, _.value)
  }

  case class LongString(value: String) extends AmqpType
  object LongString {
    val codec: Codec[LongString] =
      variableSizeBitsLong(int64, utf8).xmap(LongString.apply, _.value)
  }

  case class AmqpByte(value: Byte) extends AmqpType
  object AmqpByte {
    val codec: Codec[AmqpByte] =
      ubyte(1).xmap(AmqpByte.apply, _.value)
  }

  case class AmqpShort(value: Int) extends AmqpType
  object AmqpShort {
    val codec: Codec[AmqpShort] =
      uint16.xmap(AmqpShort.apply, _.value)
  }

  case class AmqpLong(value: Int) extends AmqpType
  object AmqpLong {
    val codec: Codec[AmqpLong] =
      int32.xmap(AmqpLong.apply, _.value)
  }

  case class AmqpLongLong(value: Long) extends AmqpType
  object AmqpLongLong {
    val codec: Codec[AmqpLongLong] = long(64).xmap(AmqpLongLong.apply, _.value)
  }

  case class AmqpBoolean(value: Boolean) extends AmqpType

  class Table extends AmqpType
  object Table {
    val codec: Codec[Table] = ???
  }

}
