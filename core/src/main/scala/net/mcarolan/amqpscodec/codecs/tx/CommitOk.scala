package net.mcarolan.amqpscodec.codecs.tx

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Tx.CommitOk
import net.mcarolan.amqpscodec.AmqpTypes._

object CommitOk {
    val TxCommitOkEncoder: Encoder[CommitOk] =
      Encoder[CommitOk] { value: CommitOk =>
        for {

        } yield 
      }
}
