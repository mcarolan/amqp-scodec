package net.mcarolan.amqpscodec.codecs.channel

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Channel
import scodec.bits.BitVector

object CloseOk {
    val ChannelCloseOkEncoder: Encoder[Channel.CloseOk] =
      Encoder[Channel.CloseOk] { _: Channel.CloseOk => Attempt.successful(BitVector.empty) }
    val ChannelCloseOkDecoder: Decoder[Channel.CloseOk] =
      Decoder[Channel.CloseOk] { bv: BitVector => Attempt.Successful(DecodeResult(Channel.CloseOk(), bv)) }
}
