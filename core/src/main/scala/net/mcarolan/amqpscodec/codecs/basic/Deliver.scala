package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Deliver
import net.mcarolan.amqpscodec.AmqpTypes._

object Deliver {
    val BasicDeliverEncoder: Encoder[Deliver] =
      Encoder[Deliver] { value: Deliver =>
        for {
          consumerTag <- ShortString.codec.encode(value.consumerTag)
          deliveryTag <- AmqpLongLong.codec.encode(value.deliveryTag)
          redelivered <- AmqpBoolean.codec.encode(value.redelivered)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
        } yield consumerTag ++ deliveryTag ++ redelivered ++ exchange ++ routingKey
      }
}
