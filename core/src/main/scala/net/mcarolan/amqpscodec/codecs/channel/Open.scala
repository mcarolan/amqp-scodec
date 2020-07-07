package net.mcarolan.amqpscodec.codecs.channel

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Channel
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object Open {
    val ChannelOpenEncoder: Encoder[Channel.Open] =
      Encoder[Channel.Open] { value: Channel.Open =>
        for {
          reserved1 <- ShortString.codec.encode(value.reserved1)
        } yield reserved1
      }

    val ChannelOpenDecoder: Decoder[Channel.Open] =
      Decoder[Channel.Open] { bv : BitVector =>
        for {
          reserved1 <- ShortString.codec.decode(bv)
        } yield DecodeResult(
            Channel.Open(reserved1.value),
            reserved1.remainder
          )
      }
}
