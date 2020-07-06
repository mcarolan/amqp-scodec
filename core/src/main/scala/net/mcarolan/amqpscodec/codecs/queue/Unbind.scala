package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.Unbind
import net.mcarolan.amqpscodec.AmqpTypes._

object Unbind {
    val QueueUnbindEncoder: Encoder[Unbind] =
      Encoder[Unbind] { value: Unbind =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ queue ++ exchange ++ routingKey ++ arguments
      }
}
