package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.Declare
import net.mcarolan.amqpscodec.AmqpTypes._

object Declare {
    val QueueDeclareEncoder: Encoder[Declare] =
      Encoder[Declare] { value: Declare =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          passive <- AmqpBoolean.codec.encode(value.passive)
          durable <- AmqpBoolean.codec.encode(value.durable)
          exclusive <- AmqpBoolean.codec.encode(value.exclusive)
          autoDelete <- AmqpBoolean.codec.encode(value.autoDelete)
          noWait <- AmqpBoolean.codec.encode(value.noWait)
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ queue ++ passive ++ durable ++ exclusive ++ autoDelete ++ noWait ++ arguments
      }
}
