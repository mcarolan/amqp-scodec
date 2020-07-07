package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Queue
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object DeleteOk {
    val QueueDeleteOkEncoder: Encoder[Queue.DeleteOk] =
      Encoder[Queue.DeleteOk] { value: Queue.DeleteOk =>
        for {
          messageCount <- AmqpLong.codec.encode(value.messageCount)
        } yield messageCount
      }

    val QueueDeleteOkDecoder: Decoder[Queue.DeleteOk] =
      Decoder[Queue.DeleteOk] { bv : BitVector =>
        for {
          messageCount <- AmqpLong.codec.decode(bv)
        } yield DecodeResult(
            Queue.DeleteOk(messageCount.value),
            messageCount.remainder
          )
      }
}
