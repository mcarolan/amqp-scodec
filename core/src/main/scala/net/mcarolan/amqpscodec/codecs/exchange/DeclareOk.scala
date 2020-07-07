package net.mcarolan.amqpscodec.codecs.exchange

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Exchange
import scodec.bits.BitVector

object DeclareOk {
    val ExchangeDeclareOkEncoder: Encoder[Exchange.DeclareOk] =
      Encoder[Exchange.DeclareOk] { _: Exchange.DeclareOk => Attempt.successful(BitVector.empty) }
    val ExchangeDeclareOkDecoder: Decoder[Exchange.DeclareOk] =
      Decoder[Exchange.DeclareOk] { bv: BitVector => Attempt.Successful(DecodeResult(Exchange.DeclareOk(), bv)) }
}
