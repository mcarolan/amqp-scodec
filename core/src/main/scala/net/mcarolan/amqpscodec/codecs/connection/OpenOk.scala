package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.OpenOk
import net.mcarolan.amqpscodec.AmqpTypes._

object OpenOk {
    val ConnectionOpenOkEncoder: Encoder[OpenOk] =
      Encoder[OpenOk] { value: OpenOk =>
        for {
          reserved1 <- ShortString.codec.encode(value.reserved1)
        } yield reserved1
      }
}
