package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Get
import net.mcarolan.amqpscodec.AmqpTypes._

object Get {
    val BasicGetEncoder: Encoder[Get] =
      Encoder[Get] { value: Get =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          noAck <- AmqpBoolean.codec.encode(value.noAck)
        } yield reserved1 ++ queue ++ noAck
      }
}
