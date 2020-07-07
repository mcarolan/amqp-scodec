package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object Secure {
    val ConnectionSecureEncoder: Encoder[Connection.Secure] =
      Encoder[Connection.Secure] { value: Connection.Secure =>
        for {
          challenge <- LongString.codec.encode(value.challenge)
        } yield challenge
      }

    val ConnectionSecureDecoder: Decoder[Connection.Secure] =
      Decoder[Connection.Secure] { bv : BitVector =>
        for {
          challenge <- LongString.codec.decode(bv)
        } yield DecodeResult(
            Connection.Secure(challenge.value),
            challenge.remainder
          )
      }
}
