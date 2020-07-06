package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Publish
import net.mcarolan.amqpscodec.AmqpTypes._

object Publish {
    val BasicPublishEncoder: Encoder[Publish] =
      Encoder[Publish] { value: Publish =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
          mandatory <- AmqpBoolean.codec.encode(value.mandatory)
          immediate <- AmqpBoolean.codec.encode(value.immediate)
        } yield reserved1 ++ exchange ++ routingKey ++ mandatory ++ immediate
      }
}
