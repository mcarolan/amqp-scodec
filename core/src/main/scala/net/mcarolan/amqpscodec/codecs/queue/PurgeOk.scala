package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Queue
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object PurgeOk {
    val QueuePurgeOkEncoder: Encoder[Queue.PurgeOk] =
      Encoder[Queue.PurgeOk] { value: Queue.PurgeOk =>
        for {
          messageCount <- AmqpLong.codec.encode(value.messageCount)
        } yield messageCount
      }

    val QueuePurgeOkDecoder: Decoder[Queue.PurgeOk] =
      Decoder[Queue.PurgeOk] { bv : BitVector =>
        for {
          messageCount <- AmqpLong.codec.decode(bv)
        } yield DecodeResult(
            Queue.PurgeOk(messageCount.value),
            messageCount.remainder
          )
      }
}
