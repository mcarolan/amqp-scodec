package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Ack {
    val BasicAckEncoder: Encoder[Basic.Ack] =
      Encoder[Basic.Ack] { value: Basic.Ack =>
        for {
          deliveryTag <- AmqpLongLong.codec.encode(value.deliveryTag)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.multiple))
        } yield deliveryTag ++ booleans0
      }

    val BasicAckDecoder: Decoder[Basic.Ack] =
      Decoder[Basic.Ack] { bv : BitVector =>
        for {
          deliveryTag <- AmqpLongLong.codec.decode(bv)
          booleans0 <- AmqpBoolean.decoder1.decode(deliveryTag.remainder)
          Seq(multiple) = booleans0.value
        } yield DecodeResult(
            Basic.Ack(deliveryTag.value, multiple),
            booleans0.remainder
          )
      }
}
