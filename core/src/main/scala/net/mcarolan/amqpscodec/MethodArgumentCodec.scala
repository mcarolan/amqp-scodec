package net.mcarolan.amqpscodec

import cats.data.NonEmptyList
import scodec.{Attempt, DecodeResult, Decoder, Encoder, Err}
import scodec.bits.BitVector

import scala.annotation.tailrec

object MethodArgumentCodec {

  val booleansEncoder: Encoder[NonEmptyList[Boolean]] = Encoder[NonEmptyList[Boolean]] { booleans: NonEmptyList[Boolean] =>
    Attempt.successful(booleans.toList.grouped(8).toList.map { group =>
      if (group.size == 8)
        BitVector.bits(group)
      else
        BitVector.bits((1 to (8 - group.size)).map(_ => false) ++ group)
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

  def booleansDecoder(n: Int): Decoder[Seq[Boolean]] = Decoder[Seq[Boolean]] { bv: BitVector =>
    if (n == 0) {
      Attempt.failure(Err.General("booleansDecoder with n = 0", List.empty))
    }
    else {
      val bytesToTake = (n - 1) / 8 + 1
      val bitsToTake = bytesToTake * 8
      val bits = bv.take(bitsToTake)
      Attempt.Successful(DecodeResult(
        toBooleans(bits.takeRight(n), Seq.empty),
        bv.drop(bitsToTake)))
    }
  }

//  @tailrec
//  private def encode(booleans: Option[NonEmptyList[AmqpTypes.AmqpBoolean]], remaining: Seq[AmqpTypes.AmqpType], accAttempt: Attempt[BitVector]): Attempt[BitVector] =
//    (remaining.headOption, booleans, accAttempt) match {
//      case (_, _, fail: Attempt.Failure) => fail
//      case (Some(boolean: AmqpTypes.AmqpBoolean), Some(booleans), Attempt.Successful(acc)) =>
//        encode(Some(booleans :+ boolean), remaining.tail, Attempt.successful(acc))
//      case (Some(boolean: AmqpTypes.AmqpBoolean), None, Attempt.Successful(acc)) =>
//        encode(Some(NonEmptyList.of(boolean)), remaining.tail, Attempt.successful(acc))
//      case (Some(other: AmqpTypes.UnpackedAmqpType), Some(booleans), Attempt.Successful(acc)) =>
//        val nextAcc =
//          booleansEncoder
//            .encode(booleans.map(_.value))
//            .flatMap(booleanBits => other.encode.map(acc ++ booleanBits ++ _))
//        encode(None, remaining.tail, nextAcc)
//      case (Some(other: AmqpTypes.UnpackedAmqpType), None, Attempt.Successful(acc)) =>
//        other.encode.map(acc ++ _)
//      case (None, Some(booleans), Attempt.Successful(acc)) =>
//        booleansEncoder.encode(booleans.map(_.value)).map(acc ++ _)
//      case (None, None, acc) =>
//        acc
//    }

//  val encoder: Encoder[Seq[AmqpTypes.AmqpType]] =
//    Encoder[Seq[AmqpTypes.AmqpType]] { values: Seq[AmqpTypes.AmqpType] =>
//      encode(None, values, Attempt.successful(BitVector.empty))
//    }

}
