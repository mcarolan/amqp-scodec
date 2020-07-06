package net.mcarolan.amqpscodec.codecs.tx

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Tx.SelectOk
import net.mcarolan.amqpscodec.AmqpTypes._

object SelectOk {
    val TxSelectOkEncoder: Encoder[SelectOk] =
      Encoder[SelectOk] { value: SelectOk =>
        for {

        } yield 
      }
}
