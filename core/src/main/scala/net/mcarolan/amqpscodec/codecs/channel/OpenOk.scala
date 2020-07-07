package net.mcarolan.amqpscodec.codecs.channel

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Channel
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object OpenOk {
    val ChannelOpenOkEncoder: Encoder[Channel.OpenOk] =
      Encoder[Channel.OpenOk] { value: Channel.OpenOk =>
        for {
          reserved1 <- LongString.codec.encode(value.reserved1)
        } yield reserved1
      }

    val ChannelOpenOkDecoder: Decoder[Channel.OpenOk] =
      Decoder[Channel.OpenOk] { bv : BitVector =>
        for {
          reserved1 <- LongString.codec.decode(bv)
        } yield DecodeResult(
            Channel.OpenOk(reserved1.value),
            reserved1.remainder
          )
      }
}
