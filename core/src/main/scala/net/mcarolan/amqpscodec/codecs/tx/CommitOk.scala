package net.mcarolan.amqpscodec.codecs.tx

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Tx
import scodec.bits.BitVector

object CommitOk {
    val TxCommitOkEncoder: Encoder[Tx.CommitOk] =
      Encoder[Tx.CommitOk] { _: Tx.CommitOk => Attempt.successful(BitVector.empty) }
    val TxCommitOkDecoder: Decoder[Tx.CommitOk] =
      Decoder[Tx.CommitOk] { bv: BitVector => Attempt.Successful(DecodeResult(Tx.CommitOk(), bv)) }
}
