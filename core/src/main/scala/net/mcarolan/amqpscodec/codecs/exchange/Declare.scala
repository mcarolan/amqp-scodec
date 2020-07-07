package net.mcarolan.amqpscodec.codecs.exchange

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Exchange
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Declare {
    val ExchangeDeclareEncoder: Encoder[Exchange.Declare] =
      Encoder[Exchange.Declare] { value: Exchange.Declare =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          exchange <- ShortString.codec.encode(value.exchange)
          `type` <- ShortString.codec.encode(value.`type`)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.passive, value.durable, value.reserved2, value.reserved3, value.noWait))
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ exchange ++ `type` ++ booleans0 ++ arguments
      }

    val ExchangeDeclareDecoder: Decoder[Exchange.Declare] =
      Decoder[Exchange.Declare] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          exchange <- ShortString.codec.decode(reserved1.remainder)
          `type` <- ShortString.codec.decode(exchange.remainder)
          booleans0 <- AmqpBoolean.decoder5.decode(`type`.remainder)
          Seq(passive, durable, reserved2, reserved3, noWait) = booleans0.value
          arguments <- Table.codec.decode(booleans0.remainder)
        } yield DecodeResult(
            Exchange.Declare(reserved1.value, exchange.value, `type`.value, passive, durable, reserved2, reserved3, noWait, arguments.value),
            arguments.remainder
          )
      }
}
