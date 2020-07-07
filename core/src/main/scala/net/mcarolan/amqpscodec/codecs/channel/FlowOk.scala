package net.mcarolan.amqpscodec.codecs.channel

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Channel
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object FlowOk {
    val ChannelFlowOkEncoder: Encoder[Channel.FlowOk] =
      Encoder[Channel.FlowOk] { value: Channel.FlowOk =>
        for {
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.active))
        } yield booleans0
      }

    val ChannelFlowOkDecoder: Decoder[Channel.FlowOk] =
      Decoder[Channel.FlowOk] { bv : BitVector =>
        for {
          booleans0 <- AmqpBoolean.decoder1.decode(bv)
          Seq(active) = booleans0.value
        } yield DecodeResult(
            Channel.FlowOk(active),
            booleans0.remainder
          )
      }
}
