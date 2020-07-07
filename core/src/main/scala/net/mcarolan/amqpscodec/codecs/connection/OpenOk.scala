package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object OpenOk {
    val ConnectionOpenOkEncoder: Encoder[Connection.OpenOk] =
      Encoder[Connection.OpenOk] { value: Connection.OpenOk =>
        for {
          reserved1 <- ShortString.codec.encode(value.reserved1)
        } yield reserved1
      }

    val ConnectionOpenOkDecoder: Decoder[Connection.OpenOk] =
      Decoder[Connection.OpenOk] { bv : BitVector =>
        for {
          reserved1 <- ShortString.codec.decode(bv)
        } yield DecodeResult(
            Connection.OpenOk(reserved1.value),
            reserved1.remainder
          )
      }
}
