package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.PurgeOk
import net.mcarolan.amqpscodec.AmqpTypes._

object PurgeOk {
    val QueuePurgeOkEncoder: Encoder[PurgeOk] =
      Encoder[PurgeOk] { value: PurgeOk =>
        for {
          messageCount <- AmqpLong.codec.encode(value.messageCount)
        } yield messageCount
      }
}
