package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Queue
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Bind {
    val QueueBindEncoder: Encoder[Queue.Bind] =
      Encoder[Queue.Bind] { value: Queue.Bind =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          exchange <- ShortString.codec.encode(value.exchange)
          routingKey <- ShortString.codec.encode(value.routingKey)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.noWait))
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ queue ++ exchange ++ routingKey ++ booleans0 ++ arguments
      }

    val QueueBindDecoder: Decoder[Queue.Bind] =
      Decoder[Queue.Bind] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          exchange <- ShortString.codec.decode(queue.remainder)
          routingKey <- ShortString.codec.decode(exchange.remainder)
          booleans0 <- AmqpBoolean.decoder1.decode(routingKey.remainder)
          Seq(noWait) = booleans0.value
          arguments <- Table.codec.decode(booleans0.remainder)
        } yield DecodeResult(
            Queue.Bind(reserved1.value, queue.value, exchange.value, routingKey.value, noWait, arguments.value),
            arguments.remainder
          )
      }
}
