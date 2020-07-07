package net.mcarolan.amqpscodec.codecs.tx

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Tx
import scodec.bits.BitVector

object Commit {
    val TxCommitEncoder: Encoder[Tx.Commit] =
      Encoder[Tx.Commit] { _: Tx.Commit => Attempt.successful(BitVector.empty) }
    val TxCommitDecoder: Decoder[Tx.Commit] =
      Decoder[Tx.Commit] { bv: BitVector => Attempt.Successful(DecodeResult(Tx.Commit(), bv)) }
}
