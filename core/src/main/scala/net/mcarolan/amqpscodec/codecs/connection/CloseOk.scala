package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.CloseOk
import net.mcarolan.amqpscodec.AmqpTypes._

object CloseOk {
    val ConnectionCloseOkEncoder: Encoder[CloseOk] =
      Encoder[CloseOk] { value: CloseOk =>
        for {

        } yield 
      }
}
