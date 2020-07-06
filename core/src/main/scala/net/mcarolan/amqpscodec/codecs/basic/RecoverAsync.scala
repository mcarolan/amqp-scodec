package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.RecoverAsync
import net.mcarolan.amqpscodec.AmqpTypes._

object RecoverAsync {
    val BasicRecoverAsyncEncoder: Encoder[RecoverAsync] =
      Encoder[RecoverAsync] { value: RecoverAsync =>
        for {
          requeue <- AmqpBoolean.codec.encode(value.requeue)
        } yield requeue
      }
}
