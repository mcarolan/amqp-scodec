package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Qos
import net.mcarolan.amqpscodec.AmqpTypes._

object Qos {
    val BasicQosEncoder: Encoder[Qos] =
      Encoder[Qos] { value: Qos =>
        for {
          prefetchSize <- AmqpLong.codec.encode(value.prefetchSize)
          prefetchCount <- AmqpShort.codec.encode(value.prefetchCount)
          global <- AmqpBoolean.codec.encode(value.global)
        } yield prefetchSize ++ prefetchCount ++ global
      }
}
