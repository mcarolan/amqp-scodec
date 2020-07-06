package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.SecureOk
import net.mcarolan.amqpscodec.AmqpTypes._

object SecureOk {
    val ConnectionSecureOkEncoder: Encoder[SecureOk] =
      Encoder[SecureOk] { value: SecureOk =>
        for {
          response <- LongString.codec.encode(value.response)
        } yield response
      }
}
