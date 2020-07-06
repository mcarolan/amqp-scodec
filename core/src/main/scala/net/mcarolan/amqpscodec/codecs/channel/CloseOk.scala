package net.mcarolan.amqpscodec.codecs.channel

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Channel.CloseOk
import net.mcarolan.amqpscodec.AmqpTypes._

object CloseOk {
    val ChannelCloseOkEncoder: Encoder[CloseOk] =
      Encoder[CloseOk] { value: CloseOk =>
        for {

        } yield 
      }
}
