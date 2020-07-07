package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Queue
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Purge {
    val QueuePurgeEncoder: Encoder[Queue.Purge] =
      Encoder[Queue.Purge] { value: Queue.Purge =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.noWait))
        } yield reserved1 ++ queue ++ booleans0
      }

    val QueuePurgeDecoder: Decoder[Queue.Purge] =
      Decoder[Queue.Purge] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          booleans0 <- AmqpBoolean.decoder1.decode(queue.remainder)
          Seq(noWait) = booleans0.value
        } yield DecodeResult(
            Queue.Purge(reserved1.value, queue.value, noWait),
            booleans0.remainder
          )
      }
}
