package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.RecoverOk
import net.mcarolan.amqpscodec.AmqpTypes._

object RecoverOk {
    val BasicRecoverOkEncoder: Encoder[RecoverOk] =
      Encoder[RecoverOk] { value: RecoverOk =>
        for {

        } yield 
      }
}
