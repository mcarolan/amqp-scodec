package net.mcarolan.amqpscodec.codecs.exchange

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Exchange.Declare
import net.mcarolan.amqpscodec.AmqpTypes._

object Declare {
    val ExchangeDeclareEncoder: Encoder[Declare] =
      Encoder[Declare] { value: Declare =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          exchange <- ShortString.codec.encode(value.exchange)
          `type` <- ShortString.codec.encode(value.`type`)
          passive <- AmqpBoolean.codec.encode(value.passive)
          durable <- AmqpBoolean.codec.encode(value.durable)
          reserved2 <- AmqpBoolean.codec.encode(value.reserved2)
          reserved3 <- AmqpBoolean.codec.encode(value.reserved3)
          noWait <- AmqpBoolean.codec.encode(value.noWait)
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ exchange ++ `type` ++ passive ++ durable ++ reserved2 ++ reserved3 ++ noWait ++ arguments
      }
}
