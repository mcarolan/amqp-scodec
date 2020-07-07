package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object SecureOk {
    val ConnectionSecureOkEncoder: Encoder[Connection.SecureOk] =
      Encoder[Connection.SecureOk] { value: Connection.SecureOk =>
        for {
          response <- LongString.codec.encode(value.response)
        } yield response
      }

    val ConnectionSecureOkDecoder: Decoder[Connection.SecureOk] =
      Decoder[Connection.SecureOk] { bv : BitVector =>
        for {
          response <- LongString.codec.decode(bv)
        } yield DecodeResult(
            Connection.SecureOk(response.value),
            response.remainder
          )
      }
}
