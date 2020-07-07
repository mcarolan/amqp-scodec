package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Publish {
    val BasicPublishEncoder: Encoder[Basic.Publish] =
      Encoder[Basic.Publish] { value: Basic.Publish =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.mandatory, value.immediate))
        } yield reserved1 ++ exchange ++ routingKey ++ booleans0
      }

    val BasicPublishDecoder: Decoder[Basic.Publish] =
      Decoder[Basic.Publish] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          exchange <- ShortString.codec.decode(reserved1.remainder)
          routingKey <- ShortString.codec.decode(exchange.remainder)
          booleans0 <- AmqpBoolean.decoder2.decode(routingKey.remainder)
          Seq(mandatory, immediate) = booleans0.value
        } yield DecodeResult(
            Basic.Publish(reserved1.value, exchange.value, routingKey.value, mandatory, immediate),
            booleans0.remainder
          )
      }
}
