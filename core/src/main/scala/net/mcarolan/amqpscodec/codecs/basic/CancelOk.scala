package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.CancelOk
import net.mcarolan.amqpscodec.AmqpTypes._

object CancelOk {
    val BasicCancelOkEncoder: Encoder[CancelOk] =
      Encoder[CancelOk] { value: CancelOk =>
        for {
          consumerTag <- ShortString.codec.encode(value.consumerTag)
        } yield consumerTag
      }
}
