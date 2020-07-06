package net.mcarolan.amqpscodec.codecs.exchange

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Exchange.Delete
import net.mcarolan.amqpscodec.AmqpTypes._

object Delete {
    val ExchangeDeleteEncoder: Encoder[Delete] =
      Encoder[Delete] { value: Delete =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          exchange <- ShortString.codec.encode(value.exchange)
          ifUnused <- AmqpBoolean.codec.encode(value.ifUnused)
          noWait <- AmqpBoolean.codec.encode(value.noWait)
        } yield reserved1 ++ exchange ++ ifUnused ++ noWait
      }
}
