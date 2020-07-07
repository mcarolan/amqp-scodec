package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object StartOk {
    val ConnectionStartOkEncoder: Encoder[Connection.StartOk] =
      Encoder[Connection.StartOk] { value: Connection.StartOk =>
        for {
          clientProperties <- Table.codec.encode(value.clientProperties)
          mechanism <- ShortString.codec.encode(value.mechanism)
          response <- LongString.codec.encode(value.response)
          locale <- ShortString.codec.encode(value.locale)
        } yield clientProperties ++ mechanism ++ response ++ locale
      }

    val ConnectionStartOkDecoder: Decoder[Connection.StartOk] =
      Decoder[Connection.StartOk] { bv : BitVector =>
        for {
          clientProperties <- Table.codec.decode(bv)
          mechanism <- ShortString.codec.decode(clientProperties.remainder)
          response <- LongString.codec.decode(mechanism.remainder)
          locale <- ShortString.codec.decode(response.remainder)
        } yield DecodeResult(
            Connection.StartOk(clientProperties.value, mechanism.value, response.value, locale.value),
            locale.remainder
          )
      }
}
