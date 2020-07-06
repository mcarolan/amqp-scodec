package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.GetOk
import net.mcarolan.amqpscodec.AmqpTypes._

object GetOk {
    val BasicGetOkEncoder: Encoder[GetOk] =
      Encoder[GetOk] { value: GetOk =>
        for {
          deliveryTag <- AmqpLongLong.codec.encode(value.deliveryTag)
          redelivered <- AmqpBoolean.codec.encode(value.redelivered)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
          messageCount <- AmqpLong.codec.encode(value.messageCount)
        } yield deliveryTag ++ redelivered ++ exchange ++ routingKey ++ messageCount
      }
}
