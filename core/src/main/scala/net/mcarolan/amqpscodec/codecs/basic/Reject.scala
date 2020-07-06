package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Reject
import net.mcarolan.amqpscodec.AmqpTypes._

object Reject {
    val BasicRejectEncoder: Encoder[Reject] =
      Encoder[Reject] { value: Reject =>
        for {
          deliveryTag <- AmqpLongLong.codec.encode(value.deliveryTag)
          requeue <- AmqpBoolean.codec.encode(value.requeue)
        } yield deliveryTag ++ requeue
      }
}
