package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.Delete
import net.mcarolan.amqpscodec.AmqpTypes._

object Delete {
    val QueueDeleteEncoder: Encoder[Delete] =
      Encoder[Delete] { value: Delete =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          ifUnused <- AmqpBoolean.codec.encode(value.ifUnused)
          ifEmpty <- AmqpBoolean.codec.encode(value.ifEmpty)
          noWait <- AmqpBoolean.codec.encode(value.noWait)
        } yield reserved1 ++ queue ++ ifUnused ++ ifEmpty ++ noWait
      }
}
