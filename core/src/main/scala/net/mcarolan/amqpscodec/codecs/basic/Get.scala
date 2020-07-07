package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Get {
    val BasicGetEncoder: Encoder[Basic.Get] =
      Encoder[Basic.Get] { value: Basic.Get =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.noAck))
        } yield reserved1 ++ queue ++ booleans0
      }

    val BasicGetDecoder: Decoder[Basic.Get] =
      Decoder[Basic.Get] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          booleans0 <- AmqpBoolean.decoder1.decode(queue.remainder)
          Seq(noAck) = booleans0.value
        } yield DecodeResult(
            Basic.Get(reserved1.value, queue.value, noAck),
            booleans0.remainder
          )
      }
}
