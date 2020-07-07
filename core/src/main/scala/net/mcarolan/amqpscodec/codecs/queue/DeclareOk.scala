package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Queue
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object DeclareOk {
    val QueueDeclareOkEncoder: Encoder[Queue.DeclareOk] =
      Encoder[Queue.DeclareOk] { value: Queue.DeclareOk =>
        for {
          queue <- ShortString.codec.encode(value.queue)
          messageCount <- AmqpLong.codec.encode(value.messageCount)
          consumerCount <- AmqpLong.codec.encode(value.consumerCount)
        } yield queue ++ messageCount ++ consumerCount
      }

    val QueueDeclareOkDecoder: Decoder[Queue.DeclareOk] =
      Decoder[Queue.DeclareOk] { bv : BitVector =>
        for {
          queue <- ShortString.codec.decode(bv)
          messageCount <- AmqpLong.codec.decode(queue.remainder)
          consumerCount <- AmqpLong.codec.decode(messageCount.remainder)
        } yield DecodeResult(
            Queue.DeclareOk(queue.value, messageCount.value, consumerCount.value),
            consumerCount.remainder
          )
      }
}
