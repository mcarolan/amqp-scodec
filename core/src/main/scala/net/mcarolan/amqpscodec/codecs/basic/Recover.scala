package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Recover {
    val BasicRecoverEncoder: Encoder[Basic.Recover] =
      Encoder[Basic.Recover] { value: Basic.Recover =>
        for {
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.requeue))
        } yield booleans0
      }

    val BasicRecoverDecoder: Decoder[Basic.Recover] =
      Decoder[Basic.Recover] { bv : BitVector =>
        for {
          booleans0 <- AmqpBoolean.decoder1.decode(bv)
          Seq(requeue) = booleans0.value
        } yield DecodeResult(
            Basic.Recover(requeue),
            booleans0.remainder
          )
      }
}
