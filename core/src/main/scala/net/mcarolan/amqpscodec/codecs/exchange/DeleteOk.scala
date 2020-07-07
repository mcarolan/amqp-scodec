package net.mcarolan.amqpscodec.codecs.exchange

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Exchange
import scodec.bits.BitVector

object DeleteOk {
    val ExchangeDeleteOkEncoder: Encoder[Exchange.DeleteOk] =
      Encoder[Exchange.DeleteOk] { _: Exchange.DeleteOk => Attempt.successful(BitVector.empty) }
    val ExchangeDeleteOkDecoder: Decoder[Exchange.DeleteOk] =
      Decoder[Exchange.DeleteOk] { bv: BitVector => Attempt.Successful(DecodeResult(Exchange.DeleteOk(), bv)) }
}
