package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Open {
    val ConnectionOpenEncoder: Encoder[Connection.Open] =
      Encoder[Connection.Open] { value: Connection.Open =>
        for {
          virtualHost <- ShortString.codec.encode(value.virtualHost)
          reserved1 <- ShortString.codec.encode(value.reserved1)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.reserved2))
        } yield virtualHost ++ reserved1 ++ booleans0
      }

    val ConnectionOpenDecoder: Decoder[Connection.Open] =
      Decoder[Connection.Open] { bv : BitVector =>
        for {
          virtualHost <- ShortString.codec.decode(bv)
          reserved1 <- ShortString.codec.decode(virtualHost.remainder)
          booleans0 <- AmqpBoolean.decoder1.decode(reserved1.remainder)
          Seq(reserved2) = booleans0.value
        } yield DecodeResult(
            Connection.Open(virtualHost.value, reserved1.value, reserved2),
            booleans0.remainder
          )
      }
}
