package net.mcarolan.amqpscodec.codecs.tx

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Tx
import scodec.bits.BitVector

object SelectOk {
    val TxSelectOkEncoder: Encoder[Tx.SelectOk] =
      Encoder[Tx.SelectOk] { _: Tx.SelectOk => Attempt.successful(BitVector.empty) }
    val TxSelectOkDecoder: Decoder[Tx.SelectOk] =
      Decoder[Tx.SelectOk] { bv: BitVector => Attempt.Successful(DecodeResult(Tx.SelectOk(), bv)) }
}
