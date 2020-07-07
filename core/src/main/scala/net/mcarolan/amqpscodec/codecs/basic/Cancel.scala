package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Cancel {
    val BasicCancelEncoder: Encoder[Basic.Cancel] =
      Encoder[Basic.Cancel] { value: Basic.Cancel =>
        for {
          consumerTag <- ShortString.codec.encode(value.consumerTag)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.noWait))
        } yield consumerTag ++ booleans0
      }

    val BasicCancelDecoder: Decoder[Basic.Cancel] =
      Decoder[Basic.Cancel] { bv : BitVector =>
        for {
          consumerTag <- ShortString.codec.decode(bv)
          booleans0 <- AmqpBoolean.decoder1.decode(consumerTag.remainder)
          Seq(noWait) = booleans0.value
        } yield DecodeResult(
            Basic.Cancel(consumerTag.value, noWait),
            booleans0.remainder
          )
      }
}
