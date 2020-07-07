package net.mcarolan.amqpscodec.codecs.tx

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Tx
import scodec.bits.BitVector

object Rollback {
    val TxRollbackEncoder: Encoder[Tx.Rollback] =
      Encoder[Tx.Rollback] { _: Tx.Rollback => Attempt.successful(BitVector.empty) }
    val TxRollbackDecoder: Decoder[Tx.Rollback] =
      Decoder[Tx.Rollback] { bv: BitVector => Attempt.Successful(DecodeResult(Tx.Rollback(), bv)) }
}
