package net.mcarolan.amqpscodec.codecs.exchange

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Exchange.DeclareOk
import net.mcarolan.amqpscodec.AmqpTypes._

object DeclareOk {
    val ExchangeDeclareOkEncoder: Encoder[DeclareOk] =
      Encoder[DeclareOk] { value: DeclareOk =>
        for {

        } yield 
      }
}
