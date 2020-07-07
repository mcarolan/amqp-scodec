package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Queue
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object Unbind {
    val QueueUnbindEncoder: Encoder[Queue.Unbind] =
      Encoder[Queue.Unbind] { value: Queue.Unbind =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ queue ++ exchange ++ routingKey ++ arguments
      }

    val QueueUnbindDecoder: Decoder[Queue.Unbind] =
      Decoder[Queue.Unbind] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          exchange <- ShortString.codec.decode(queue.remainder)
          routingKey <- ShortString.codec.decode(exchange.remainder)
          arguments <- Table.codec.decode(routingKey.remainder)
        } yield DecodeResult(
            Queue.Unbind(reserved1.value, queue.value, exchange.value, routingKey.value, arguments.value),
            arguments.remainder
          )
      }
}
