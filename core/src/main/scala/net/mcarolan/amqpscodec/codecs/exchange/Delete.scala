package net.mcarolan.amqpscodec.codecs.exchange

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Exchange
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Delete {
    val ExchangeDeleteEncoder: Encoder[Exchange.Delete] =
      Encoder[Exchange.Delete] { value: Exchange.Delete =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          exchange <- ShortString.codec.encode(value.exchange)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.ifUnused, value.noWait))
        } yield reserved1 ++ exchange ++ booleans0
      }

    val ExchangeDeleteDecoder: Decoder[Exchange.Delete] =
      Decoder[Exchange.Delete] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          exchange <- ShortString.codec.decode(reserved1.remainder)
          booleans0 <- AmqpBoolean.decoder2.decode(exchange.remainder)
          Seq(ifUnused, noWait) = booleans0.value
        } yield DecodeResult(
            Exchange.Delete(reserved1.value, exchange.value, ifUnused, noWait),
            booleans0.remainder
          )
      }
}
