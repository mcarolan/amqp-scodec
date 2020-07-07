package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Basic
import scodec.bits.BitVector

object QosOk {
    val BasicQosOkEncoder: Encoder[Basic.QosOk] =
      Encoder[Basic.QosOk] { _: Basic.QosOk => Attempt.successful(BitVector.empty) }
    val BasicQosOkDecoder: Decoder[Basic.QosOk] =
      Decoder[Basic.QosOk] { bv: BitVector => Attempt.Successful(DecodeResult(Basic.QosOk(), bv)) }
}
