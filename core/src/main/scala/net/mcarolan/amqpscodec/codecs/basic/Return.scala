package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.Return
import net.mcarolan.amqpscodec.AmqpTypes._

object Return {
    val BasicReturnEncoder: Encoder[Return] =
      Encoder[Return] { value: Return =>
        for {
          replyCode <- AmqpShort.codec.encode(value.replyCode)
          replyText <- ShortString.codec.encode(value.replyText)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
        } yield replyCode ++ replyText ++ exchange ++ routingKey
      }
}
