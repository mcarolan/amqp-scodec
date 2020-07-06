package net.mcarolan.amqpscodec.codecs.exchange

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Exchange.DeleteOk
import net.mcarolan.amqpscodec.AmqpTypes._

object DeleteOk {
    val ExchangeDeleteOkEncoder: Encoder[DeleteOk] =
      Encoder[DeleteOk] { value: DeleteOk =>
        for {

        } yield 
      }
}
