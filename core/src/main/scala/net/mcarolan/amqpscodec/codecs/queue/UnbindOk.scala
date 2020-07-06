package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.UnbindOk
import net.mcarolan.amqpscodec.AmqpTypes._

object UnbindOk {
    val QueueUnbindOkEncoder: Encoder[UnbindOk] =
      Encoder[UnbindOk] { value: UnbindOk =>
        for {

        } yield 
      }
}
