package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.ConsumeOk
import net.mcarolan.amqpscodec.AmqpTypes._

object ConsumeOk {
    val BasicConsumeOkEncoder: Encoder[ConsumeOk] =
      Encoder[ConsumeOk] { value: ConsumeOk =>
        for {
          consumerTag <- ShortString.codec.encode(value.consumerTag)
        } yield consumerTag
      }
}
