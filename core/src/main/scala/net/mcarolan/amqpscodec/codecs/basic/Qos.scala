package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Qos {
    val BasicQosEncoder: Encoder[Basic.Qos] =
      Encoder[Basic.Qos] { value: Basic.Qos =>
        for {
          prefetchSize <- AmqpLong.codec.encode(value.prefetchSize)
          prefetchCount <- AmqpShort.codec.encode(value.prefetchCount)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.global))
        } yield prefetchSize ++ prefetchCount ++ booleans0
      }

    val BasicQosDecoder: Decoder[Basic.Qos] =
      Decoder[Basic.Qos] { bv : BitVector =>
        for {
          prefetchSize <- AmqpLong.codec.decode(bv)
          prefetchCount <- AmqpShort.codec.decode(prefetchSize.remainder)
          booleans0 <- AmqpBoolean.decoder1.decode(prefetchCount.remainder)
          Seq(global) = booleans0.value
        } yield DecodeResult(
            Basic.Qos(prefetchSize.value, prefetchCount.value, global),
            booleans0.remainder
          )
      }
}
