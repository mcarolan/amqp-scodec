package net.mcarolan.amqpscodec.codecs.tx

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Tx
import scodec.bits.BitVector

object RollbackOk {
    val TxRollbackOkEncoder: Encoder[Tx.RollbackOk] =
      Encoder[Tx.RollbackOk] { _: Tx.RollbackOk => Attempt.successful(BitVector.empty) }
    val TxRollbackOkDecoder: Decoder[Tx.RollbackOk] =
      Decoder[Tx.RollbackOk] { bv: BitVector => Attempt.Successful(DecodeResult(Tx.RollbackOk(), bv)) }
}
