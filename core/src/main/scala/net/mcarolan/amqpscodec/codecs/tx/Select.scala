package net.mcarolan.amqpscodec.codecs.tx

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Tx.Select
import net.mcarolan.amqpscodec.AmqpTypes._

object Select {
    val TxSelectEncoder: Encoder[Select] =
      Encoder[Select] { value: Select =>
        for {

        } yield 
      }
}
