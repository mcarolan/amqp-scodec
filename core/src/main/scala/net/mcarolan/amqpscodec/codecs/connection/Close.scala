package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object Close {
    val ConnectionCloseEncoder: Encoder[Connection.Close] =
      Encoder[Connection.Close] { value: Connection.Close =>
        for {
          replyCode <- AmqpShort.codec.encode(value.replyCode)
          replyText <- ShortString.codec.encode(value.replyText)
          classId <- AmqpShort.codec.encode(value.classId)
          methodId <- AmqpShort.codec.encode(value.methodId)
        } yield replyCode ++ replyText ++ classId ++ methodId
      }

    val ConnectionCloseDecoder: Decoder[Connection.Close] =
      Decoder[Connection.Close] { bv : BitVector =>
        for {
          replyCode <- AmqpShort.codec.decode(bv)
          replyText <- ShortString.codec.decode(replyCode.remainder)
          classId <- AmqpShort.codec.decode(replyText.remainder)
          methodId <- AmqpShort.codec.decode(classId.remainder)
        } yield DecodeResult(
            Connection.Close(replyCode.value, replyText.value, classId.value, methodId.value),
            methodId.remainder
          )
      }
}
