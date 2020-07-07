package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object GetEmpty {
    val BasicGetEmptyEncoder: Encoder[Basic.GetEmpty] =
      Encoder[Basic.GetEmpty] { value: Basic.GetEmpty =>
        for {
          reserved1 <- ShortString.codec.encode(value.reserved1)
        } yield reserved1
      }

    val BasicGetEmptyDecoder: Decoder[Basic.GetEmpty] =
      Decoder[Basic.GetEmpty] { bv : BitVector =>
        for {
          reserved1 <- ShortString.codec.decode(bv)
        } yield DecodeResult(
            Basic.GetEmpty(reserved1.value),
            reserved1.remainder
          )
      }
}
