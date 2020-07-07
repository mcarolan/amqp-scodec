package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object CancelOk {
    val BasicCancelOkEncoder: Encoder[Basic.CancelOk] =
      Encoder[Basic.CancelOk] { value: Basic.CancelOk =>
        for {
          consumerTag <- ShortString.codec.encode(value.consumerTag)
        } yield consumerTag
      }

    val BasicCancelOkDecoder: Decoder[Basic.CancelOk] =
      Decoder[Basic.CancelOk] { bv : BitVector =>
        for {
          consumerTag <- ShortString.codec.decode(bv)
        } yield DecodeResult(
            Basic.CancelOk(consumerTag.value),
            consumerTag.remainder
          )
      }
}
