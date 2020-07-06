package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Consume
import net.mcarolan.amqpscodec.AmqpTypes._

object Consume {
    val BasicConsumeEncoder: Encoder[Consume] =
      Encoder[Consume] { value: Consume =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          consumerTag <- ShortString.codec.encode(value.consumerTag)
          noLocal <- AmqpBoolean.codec.encode(value.noLocal)
          noAck <- AmqpBoolean.codec.encode(value.noAck)
          exclusive <- AmqpBoolean.codec.encode(value.exclusive)
          noWait <- AmqpBoolean.codec.encode(value.noWait)
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ queue ++ consumerTag ++ noLocal ++ noAck ++ exclusive ++ noWait ++ arguments
      }
}
