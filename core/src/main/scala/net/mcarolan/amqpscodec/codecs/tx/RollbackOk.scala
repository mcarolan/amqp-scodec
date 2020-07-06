package net.mcarolan.amqpscodec.codecs.tx

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Tx.RollbackOk
import net.mcarolan.amqpscodec.AmqpTypes._

object RollbackOk {
    val TxRollbackOkEncoder: Encoder[RollbackOk] =
      Encoder[RollbackOk] { value: RollbackOk =>
        for {

        } yield 
      }
}
