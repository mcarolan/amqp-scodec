package net.mcarolan.amqpscodec.codecs.channel

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Channel.Close
import net.mcarolan.amqpscodec.AmqpTypes._

object Close {
    val ChannelCloseEncoder: Encoder[Close] =
      Encoder[Close] { value: Close =>
        for {
          replyCode <- AmqpShort.codec.encode(value.replyCode)
          replyText <- ShortString.codec.encode(value.replyText)
          classId <- AmqpShort.codec.encode(value.classId)
          methodId <- AmqpShort.codec.encode(value.methodId)
        } yield replyCode ++ replyText ++ classId ++ methodId
      }
}
