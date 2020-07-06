package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.Purge
import net.mcarolan.amqpscodec.AmqpTypes._

object Purge {
    val QueuePurgeEncoder: Encoder[Purge] =
      Encoder[Purge] { value: Purge =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          noWait <- AmqpBoolean.codec.encode(value.noWait)
        } yield reserved1 ++ queue ++ noWait
      }
}
