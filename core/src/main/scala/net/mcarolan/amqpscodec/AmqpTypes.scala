package net.mcarolan.amqpscodec

import cats.data.NonEmptyList
import scodec.bits.BitVector
import scodec.{Attempt, Codec, DecodeResult, Decoder, Encoder, Err}
import scodec.codecs._

import scala.annotation.tailrec

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
  object AmqpBoolean {
    val encoder: Encoder[NonEmptyList[AmqpBoolean]] = Encoder[NonEmptyList[AmqpBoolean]] { booleans: NonEmptyList[AmqpBoolean] =>
      Attempt.successful(booleans.toList.grouped(8).toList.map { group =>
        val groupBooleans = group.map(_.value)
        if (group.size == 8)
          BitVector.bits(groupBooleans)
        else
          BitVector.bits((1 to (8 - groupBooleans.size)).map(_ => false) ++ groupBooleans)
      }.foldLeft(BitVector.empty)( (a, b) => b ++ a))
    }

    @tailrec
    private def toBooleans(bitVector: BitVector, acc: Seq[Boolean]): Seq[Boolean] =
      bitVector.headOption match {
        case Some(value) =>
          toBooleans(bitVector.tail, acc :+ value)
        case None =>
          acc
      }

    private def decoder(n: Int): Decoder[Seq[AmqpBoolean]] = Decoder[Seq[AmqpBoolean]] { bv: BitVector =>
      val bytesToTake = (n - 1) / 8 + 1
      val bitsToTake = bytesToTake * 8
      val bits = bv.take(bitsToTake)

      if (bits != bitsToTake)
        Attempt.Failure(Err.insufficientBits(bitsToTake, bits.size))
      else
        Attempt.Successful(DecodeResult(
          toBooleans(bits.takeRight(n), Seq.empty).map(AmqpBoolean.apply),
          bv.drop(bitsToTake)))
    }

    val decoder1: Decoder[Seq[AmqpBoolean]] = decoder(1)
    val decoder2: Decoder[Seq[AmqpBoolean]] = decoder(2)
    val decoder3: Decoder[Seq[AmqpBoolean]] = decoder(3)
    val decoder4: Decoder[Seq[AmqpBoolean]] = decoder(4)
    val decoder5: Decoder[Seq[AmqpBoolean]] = decoder(5)
  }

  class Table extends AmqpType
  object Table {
    val codec: Codec[Table] = ???
  }

}
