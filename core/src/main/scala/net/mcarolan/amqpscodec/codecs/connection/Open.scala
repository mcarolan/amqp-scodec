package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.Open
import net.mcarolan.amqpscodec.AmqpTypes._

object Open {
    val ConnectionOpenEncoder: Encoder[Open] =
      Encoder[Open] { value: Open =>
        for {
          virtualHost <- ShortString.codec.encode(value.virtualHost)
          reserved1 <- ShortString.codec.encode(value.reserved1)
          reserved2 <- AmqpBoolean.codec.encode(value.reserved2)
        } yield virtualHost ++ reserved1 ++ reserved2
      }
}
