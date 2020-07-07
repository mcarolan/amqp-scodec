package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object ConsumeOk {
    val BasicConsumeOkEncoder: Encoder[Basic.ConsumeOk] =
      Encoder[Basic.ConsumeOk] { value: Basic.ConsumeOk =>
        for {
          consumerTag <- ShortString.codec.encode(value.consumerTag)
        } yield consumerTag
      }

    val BasicConsumeOkDecoder: Decoder[Basic.ConsumeOk] =
      Decoder[Basic.ConsumeOk] { bv : BitVector =>
        for {
          consumerTag <- ShortString.codec.decode(bv)
        } yield DecodeResult(
            Basic.ConsumeOk(consumerTag.value),
            consumerTag.remainder
          )
      }
}
