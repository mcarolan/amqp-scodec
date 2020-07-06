package net.mcarolan.amqpscodec.codecs.channel

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Channel.FlowOk
import net.mcarolan.amqpscodec.AmqpTypes._

object FlowOk {
    val ChannelFlowOkEncoder: Encoder[FlowOk] =
      Encoder[FlowOk] { value: FlowOk =>
        for {
          active <- AmqpBoolean.codec.encode(value.active)
        } yield active
      }
}
