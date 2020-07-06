package net.mcarolan.amqpscodec.codecs.channel

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Channel.Open
import net.mcarolan.amqpscodec.AmqpTypes._

object Open {
    val ChannelOpenEncoder: Encoder[Open] =
      Encoder[Open] { value: Open =>
        for {
          reserved1 <- ShortString.codec.encode(value.reserved1)
        } yield reserved1
      }
}
