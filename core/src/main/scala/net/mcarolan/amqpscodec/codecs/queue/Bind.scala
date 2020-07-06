package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.Bind
import net.mcarolan.amqpscodec.AmqpTypes._

object Bind {
    val QueueBindEncoder: Encoder[Bind] =
      Encoder[Bind] { value: Bind =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
          noWait <- AmqpBoolean.codec.encode(value.noWait)
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ queue ++ exchange ++ routingKey ++ noWait ++ arguments
      }
}
