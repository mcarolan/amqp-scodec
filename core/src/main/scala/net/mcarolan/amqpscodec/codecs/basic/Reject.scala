package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Reject {
    val BasicRejectEncoder: Encoder[Basic.Reject] =
      Encoder[Basic.Reject] { value: Basic.Reject =>
        for {
          deliveryTag <- AmqpLongLong.codec.encode(value.deliveryTag)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.requeue))
        } yield deliveryTag ++ booleans0
      }

    val BasicRejectDecoder: Decoder[Basic.Reject] =
      Decoder[Basic.Reject] { bv : BitVector =>
        for {
          deliveryTag <- AmqpLongLong.codec.decode(bv)
          booleans0 <- AmqpBoolean.decoder1.decode(deliveryTag.remainder)
          Seq(requeue) = booleans0.value
        } yield DecodeResult(
            Basic.Reject(deliveryTag.value, requeue),
            booleans0.remainder
          )
      }
}
