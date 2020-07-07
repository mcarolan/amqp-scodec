package net.mcarolan.amqpscodec

import cats.data.NonEmptyList
import scodec.{Attempt, DecodeResult, Decoder, Encoder, Err}
import scodec.bits.BitVector

import scala.annotation.tailrec

object MethodArgumentCodec {


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
