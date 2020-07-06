package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Recover
import net.mcarolan.amqpscodec.AmqpTypes._

object Recover {
    val BasicRecoverEncoder: Encoder[Recover] =
      Encoder[Recover] { value: Recover =>
        for {
          requeue <- AmqpBoolean.codec.encode(value.requeue)
        } yield requeue
      }
}
