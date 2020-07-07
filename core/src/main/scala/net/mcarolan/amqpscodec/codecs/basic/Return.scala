package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object Return {
    val BasicReturnEncoder: Encoder[Basic.Return] =
      Encoder[Basic.Return] { value: Basic.Return =>
        for {
          replyCode <- AmqpShort.codec.encode(value.replyCode)
          replyText <- ShortString.codec.encode(value.replyText)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
        } yield replyCode ++ replyText ++ exchange ++ routingKey
      }

    val BasicReturnDecoder: Decoder[Basic.Return] =
      Decoder[Basic.Return] { bv : BitVector =>
        for {
          replyCode <- AmqpShort.codec.decode(bv)
          replyText <- ShortString.codec.decode(replyCode.remainder)
          exchange <- ShortString.codec.decode(replyText.remainder)
          routingKey <- ShortString.codec.decode(exchange.remainder)
        } yield DecodeResult(
            Basic.Return(replyCode.value, replyText.value, exchange.value, routingKey.value),
            routingKey.remainder
          )
      }
}
