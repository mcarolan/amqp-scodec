package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object GetOk {
    val BasicGetOkEncoder: Encoder[Basic.GetOk] =
      Encoder[Basic.GetOk] { value: Basic.GetOk =>
        for {
          deliveryTag <- AmqpLongLong.codec.encode(value.deliveryTag)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.redelivered))
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
          messageCount <- AmqpLong.codec.encode(value.messageCount)
        } yield deliveryTag ++ booleans0 ++ exchange ++ routingKey ++ messageCount
      }

    val BasicGetOkDecoder: Decoder[Basic.GetOk] =
      Decoder[Basic.GetOk] { bv : BitVector =>
        for {
          deliveryTag <- AmqpLongLong.codec.decode(bv)
          booleans0 <- AmqpBoolean.decoder1.decode(deliveryTag.remainder)
          Seq(redelivered) = booleans0.value
          exchange <- ShortString.codec.decode(booleans0.remainder)
          routingKey <- ShortString.codec.decode(exchange.remainder)
          messageCount <- AmqpLong.codec.decode(routingKey.remainder)
        } yield DecodeResult(
            Basic.GetOk(deliveryTag.value, redelivered, exchange.value, routingKey.value, messageCount.value),
            messageCount.remainder
          )
      }
}
