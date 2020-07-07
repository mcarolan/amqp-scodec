package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Connection
import scodec.bits.BitVector

object CloseOk {
    val ConnectionCloseOkEncoder: Encoder[Connection.CloseOk] =
      Encoder[Connection.CloseOk] { _: Connection.CloseOk => Attempt.successful(BitVector.empty) }
    val ConnectionCloseOkDecoder: Decoder[Connection.CloseOk] =
      Decoder[Connection.CloseOk] { bv: BitVector => Attempt.Successful(DecodeResult(Connection.CloseOk(), bv)) }
}
