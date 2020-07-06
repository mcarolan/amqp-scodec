package net.mcarolan.amqpscodec.codecs.queue

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Queue.BindOk
import net.mcarolan.amqpscodec.AmqpTypes._

object BindOk {
    val QueueBindOkEncoder: Encoder[BindOk] =
      Encoder[BindOk] { value: BindOk =>
        for {

        } yield 
      }
}
