package net.mcarolan.amqpscodec.codecs.channel

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Channel
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object Close {
    val ChannelCloseEncoder: Encoder[Channel.Close] =
      Encoder[Channel.Close] { value: Channel.Close =>
        for {
          replyCode <- AmqpShort.codec.encode(value.replyCode)
          replyText <- ShortString.codec.encode(value.replyText)
          classId <- AmqpShort.codec.encode(value.classId)
          methodId <- AmqpShort.codec.encode(value.methodId)
        } yield replyCode ++ replyText ++ classId ++ methodId
      }

    val ChannelCloseDecoder: Decoder[Channel.Close] =
      Decoder[Channel.Close] { bv : BitVector =>
        for {
          replyCode <- AmqpShort.codec.decode(bv)
          replyText <- ShortString.codec.decode(replyCode.remainder)
          classId <- AmqpShort.codec.decode(replyText.remainder)
          methodId <- AmqpShort.codec.decode(classId.remainder)
        } yield DecodeResult(
            Channel.Close(replyCode.value, replyText.value, classId.value, methodId.value),
            methodId.remainder
          )
      }
}
