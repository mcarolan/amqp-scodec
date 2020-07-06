package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.QosOk
import net.mcarolan.amqpscodec.AmqpTypes._

object QosOk {
    val BasicQosOkEncoder: Encoder[QosOk] =
      Encoder[QosOk] { value: QosOk =>
        for {

        } yield 
      }
}
