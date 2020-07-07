package net.mcarolan.amqpscodec.codecs.basic

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Basic
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector
import cats.data.NonEmptyList

object Consume {
    val BasicConsumeEncoder: Encoder[Basic.Consume] =
      Encoder[Basic.Consume] { value: Basic.Consume =>
        for {
          reserved1 <- AmqpShort.codec.encode(value.reserved1)
          queue <- ShortString.codec.encode(value.queue)
          consumerTag <- ShortString.codec.encode(value.consumerTag)
          booleans0 <- AmqpBoolean.encoder.encode(NonEmptyList.of(value.noLocal, value.noAck, value.exclusive, value.noWait))
          arguments <- Table.codec.encode(value.arguments)
        } yield reserved1 ++ queue ++ consumerTag ++ booleans0 ++ arguments
      }

    val BasicConsumeDecoder: Decoder[Basic.Consume] =
      Decoder[Basic.Consume] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          consumerTag <- ShortString.codec.decode(queue.remainder)
          booleans0 <- AmqpBoolean.decoder4.decode(consumerTag.remainder)
          Seq(noLocal, noAck, exclusive, noWait) = booleans0.value
          arguments <- Table.codec.decode(booleans0.remainder)
        } yield DecodeResult(
            Basic.Consume(reserved1.value, queue.value, consumerTag.value, noLocal, noAck, exclusive, noWait, arguments.value),
            arguments.remainder
          )
      }
}
