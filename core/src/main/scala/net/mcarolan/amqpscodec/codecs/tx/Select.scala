package net.mcarolan.amqpscodec.codecs.tx

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Tx
import scodec.bits.BitVector

object Select {
    val TxSelectEncoder: Encoder[Tx.Select] =
      Encoder[Tx.Select] { _: Tx.Select => Attempt.successful(BitVector.empty) }
    val TxSelectDecoder: Decoder[Tx.Select] =
      Decoder[Tx.Select] { bv: BitVector => Attempt.Successful(DecodeResult(Tx.Select(), bv)) }
}
