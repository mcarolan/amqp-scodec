package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.Secure
import net.mcarolan.amqpscodec.AmqpTypes._

object Secure {
    val ConnectionSecureEncoder: Encoder[Secure] =
      Encoder[Secure] { value: Secure =>
        for {
          challenge <- LongString.codec.encode(value.challenge)
        } yield challenge
      }
}
