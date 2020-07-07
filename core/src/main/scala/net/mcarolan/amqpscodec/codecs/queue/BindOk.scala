package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Queue
import scodec.bits.BitVector

object BindOk {
    val QueueBindOkEncoder: Encoder[Queue.BindOk] =
      Encoder[Queue.BindOk] { _: Queue.BindOk => Attempt.successful(BitVector.empty) }
    val QueueBindOkDecoder: Decoder[Queue.BindOk] =
      Decoder[Queue.BindOk] { bv: BitVector => Attempt.Successful(DecodeResult(Queue.BindOk(), bv)) }
}
