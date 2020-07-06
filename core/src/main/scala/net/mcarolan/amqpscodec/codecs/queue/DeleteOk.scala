package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.DeleteOk
import net.mcarolan.amqpscodec.AmqpTypes._

object DeleteOk {
    val QueueDeleteOkEncoder: Encoder[DeleteOk] =
      Encoder[DeleteOk] { value: DeleteOk =>
        for {
          messageCount <- AmqpLong.codec.encode(value.messageCount)
        } yield messageCount
      }
}
