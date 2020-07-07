package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Basic
import scodec.bits.BitVector

object RecoverOk {
    val BasicRecoverOkEncoder: Encoder[Basic.RecoverOk] =
      Encoder[Basic.RecoverOk] { _: Basic.RecoverOk => Attempt.successful(BitVector.empty) }
    val BasicRecoverOkDecoder: Decoder[Basic.RecoverOk] =
      Decoder[Basic.RecoverOk] { bv: BitVector => Attempt.Successful(DecodeResult(Basic.RecoverOk(), bv)) }
}
