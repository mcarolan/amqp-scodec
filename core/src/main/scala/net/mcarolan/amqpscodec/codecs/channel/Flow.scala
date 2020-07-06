package net.mcarolan.amqpscodec.codecs.channel

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Channel.Flow
import net.mcarolan.amqpscodec.AmqpTypes._

object Flow {
    val ChannelFlowEncoder: Encoder[Flow] =
      Encoder[Flow] { value: Flow =>
        for {
          active <- AmqpBoolean.codec.encode(value.active)
        } yield active
      }
}
