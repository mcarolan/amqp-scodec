package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Cancel
import net.mcarolan.amqpscodec.AmqpTypes._

object Cancel {
    val BasicCancelEncoder: Encoder[Cancel] =
      Encoder[Cancel] { value: Cancel =>
        for {
          consumerTag <- ShortString.codec.encode(value.consumerTag)
          noWait <- AmqpBoolean.codec.encode(value.noWait)
        } yield consumerTag ++ noWait
      }
}
