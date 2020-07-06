package net.mcarolan.amqpscodec.codecs.tx

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Tx.Rollback
import net.mcarolan.amqpscodec.AmqpTypes._

object Rollback {
    val TxRollbackEncoder: Encoder[Rollback] =
      Encoder[Rollback] { value: Rollback =>
        for {

        } yield 
      }
}
