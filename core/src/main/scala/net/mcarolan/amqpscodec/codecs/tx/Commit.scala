package net.mcarolan.amqpscodec.codecs.tx

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Tx.Commit
import net.mcarolan.amqpscodec.AmqpTypes._

object Commit {
    val TxCommitEncoder: Encoder[Commit] =
      Encoder[Commit] { value: Commit =>
        for {

        } yield 
      }
}
