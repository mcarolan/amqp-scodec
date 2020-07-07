package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Queue
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Delete {
    val QueueDeleteEncoder: Encoder[Queue.Delete] =
      Encoder[Queue.Delete] { value: Queue.Delete =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.ifUnused, value.ifEmpty, value.noWait))
        } yield reserved1 ++ queue ++ booleans0
      }

    val QueueDeleteDecoder: Decoder[Queue.Delete] =
      Decoder[Queue.Delete] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          booleans0 <- AmqpBoolean.decoder3.decode(queue.remainder)
          Seq(ifUnused, ifEmpty, noWait) = booleans0.value
        } yield DecodeResult(
            Queue.Delete(reserved1.value, queue.value, ifUnused, ifEmpty, noWait),
            booleans0.remainder
          )
      }
}
