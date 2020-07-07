package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object RecoverAsync {
    val BasicRecoverAsyncEncoder: Encoder[Basic.RecoverAsync] =
      Encoder[Basic.RecoverAsync] { value: Basic.RecoverAsync =>
        for {
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.requeue))
        } yield booleans0
      }

    val BasicRecoverAsyncDecoder: Decoder[Basic.RecoverAsync] =
      Decoder[Basic.RecoverAsync] { bv : BitVector =>
        for {
          booleans0 <- AmqpBoolean.decoder1.decode(bv)
          Seq(requeue) = booleans0.value
        } yield DecodeResult(
            Basic.RecoverAsync(requeue),
            booleans0.remainder
          )
      }
}
