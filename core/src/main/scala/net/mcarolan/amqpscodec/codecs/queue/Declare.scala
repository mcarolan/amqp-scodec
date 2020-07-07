package net.mcarolan.amqpscodec.codecs.queue

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Queue
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Declare {
    val QueueDeclareEncoder: Encoder[Queue.Declare] =
      Encoder[Queue.Declare] { value: Queue.Declare =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.passive, value.durable, value.exclusive, value.autoDelete, value.noWait))
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ queue ++ booleans0 ++ arguments
      }

    val QueueDeclareDecoder: Decoder[Queue.Declare] =
      Decoder[Queue.Declare] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          booleans0 <- AmqpBoolean.decoder5.decode(queue.remainder)
          Seq(passive, durable, exclusive, autoDelete, noWait) = booleans0.value
          arguments <- Table.codec.decode(booleans0.remainder)
        } yield DecodeResult(
            Queue.Declare(reserved1.value, queue.value, passive, durable, exclusive, autoDelete, noWait, arguments.value),
            arguments.remainder
          )
      }
}
