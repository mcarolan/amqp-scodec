package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.StartOk
import net.mcarolan.amqpscodec.AmqpTypes._

object StartOk {
    val ConnectionStartOkEncoder: Encoder[StartOk] =
      Encoder[StartOk] { value: StartOk =>
        for {
          clientProperties <- Table.codec.encode(value.clientProperties)
          mechanism <- ShortString.codec.encode(value.mechanism)
          response <- LongString.codec.encode(value.response)
          locale <- ShortString.codec.encode(value.locale)
        } yield clientProperties ++ mechanism ++ response ++ locale
      }
}
