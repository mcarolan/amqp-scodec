package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.DeclareOk
import net.mcarolan.amqpscodec.AmqpTypes._

object DeclareOk {
    val QueueDeclareOkEncoder: Encoder[DeclareOk] =
      Encoder[DeclareOk] { value: DeclareOk =>
        for {
          queue <- ShortString.codec.encode(value.queue)
          messageCount <- AmqpLong.codec.encode(value.messageCount)
          consumerCount <- AmqpLong.codec.encode(value.consumerCount)
        } yield queue ++ messageCount ++ consumerCount
      }
}
