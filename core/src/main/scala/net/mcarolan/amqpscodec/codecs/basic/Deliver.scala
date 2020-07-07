package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Deliver {
    val BasicDeliverEncoder: Encoder[Basic.Deliver] =
      Encoder[Basic.Deliver] { value: Basic.Deliver =>
        for {
          consumerTag <- ShortString.codec.encode(value.consumerTag)
          deliveryTag <- AmqpLongLong.codec.encode(value.deliveryTag)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.redelivered))
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
        } yield consumerTag ++ deliveryTag ++ booleans0 ++ exchange ++ routingKey
      }

    val BasicDeliverDecoder: Decoder[Basic.Deliver] =
      Decoder[Basic.Deliver] { bv : BitVector =>
        for {
          consumerTag <- ShortString.codec.decode(bv)
          deliveryTag <- AmqpLongLong.codec.decode(consumerTag.remainder)
          booleans0 <- AmqpBoolean.decoder1.decode(deliveryTag.remainder)
          Seq(redelivered) = booleans0.value
          exchange <- ShortString.codec.decode(booleans0.remainder)
          routingKey <- ShortString.codec.decode(exchange.remainder)
        } yield DecodeResult(
            Basic.Deliver(consumerTag.value, deliveryTag.value, redelivered, exchange.value, routingKey.value),
            routingKey.remainder
          )
      }
}
