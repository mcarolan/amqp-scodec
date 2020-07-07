package net.mcarolan.amqpscodec.codecs.channel

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Channel
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Flow {
    val ChannelFlowEncoder: Encoder[Channel.Flow] =
      Encoder[Channel.Flow] { value: Channel.Flow =>
        for {
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.active))
        } yield booleans0
      }

    val ChannelFlowDecoder: Decoder[Channel.Flow] =
      Decoder[Channel.Flow] { bv : BitVector =>
        for {
          booleans0 <- AmqpBoolean.decoder1.decode(bv)
          Seq(active) = booleans0.value
        } yield DecodeResult(
            Channel.Flow(active),
            booleans0.remainder
          )
      }
}
