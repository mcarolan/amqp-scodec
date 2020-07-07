package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Attempt, DecodeResult, Decoder, Encoder}
import net.mcarolan.amqpscodec.spec.Queue
import scodec.bits.BitVector

object UnbindOk {
    val QueueUnbindOkEncoder: Encoder[Queue.UnbindOk] =
      Encoder[Queue.UnbindOk] { _: Queue.UnbindOk => Attempt.successful(BitVector.empty) }
    val QueueUnbindOkDecoder: Decoder[Queue.UnbindOk] =
      Decoder[Queue.UnbindOk] { bv: BitVector => Attempt.Successful(DecodeResult(Queue.UnbindOk(), bv)) }
}
