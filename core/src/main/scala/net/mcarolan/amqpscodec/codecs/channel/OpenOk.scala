package net.mcarolan.amqpscodec.codecs.channel

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Channel.OpenOk
import net.mcarolan.amqpscodec.AmqpTypes._

object OpenOk {
    val ChannelOpenOkEncoder: Encoder[OpenOk] =
      Encoder[OpenOk] { value: OpenOk =>
        for {
          reserved1 <- LongString.codec.encode(value.reserved1)
        } yield reserved1
      }
}
