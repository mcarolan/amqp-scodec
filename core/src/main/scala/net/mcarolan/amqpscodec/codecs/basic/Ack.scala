package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Ack
import net.mcarolan.amqpscodec.AmqpTypes._

object Ack {
    val BasicAckEncoder: Encoder[Ack] =
      Encoder[Ack] { value: Ack =>
        for {
          deliveryTag <- AmqpLongLong.codec.encode(value.deliveryTag)
          multiple <- AmqpBoolean.codec.encode(value.multiple)
        } yield deliveryTag ++ multiple
      }
}
