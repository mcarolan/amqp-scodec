package net.mcarolan.generator.generators

import net.mcarolan.generator.SpecReader

/*
TODO:
    object Consume {
      val index: AmqpShort = AmqpShort(20)
      val encoder: Encoder[Consume] = Encoder[Consume] { consume: Consume =>
        for {
          reserved1 <- AmqpShort.codec.encode(consume.reserved1)
          queue <- ShortString.codec.encode(consume.queue)
          consumerTag <- ShortString.codec.encode(consume.consumerTag)
          booleans1 <- MethodArgumentCodec.booleansEncoder.encode(NonEmptyList.of(consume.noLocal.value, consume.noAck.value, consume.exclusive.value, consume.noWait.value))
          arguments <- Table.codec.encode(consume.arguments)
        } yield reserved1 ++ queue ++ consumerTag ++ booleans1 ++ arguments
      }
      val decoder: Decoder[Consume] = Decoder[Consume] { bv : BitVector =>
        for {
          reserved1 <- AmqpShort.codec.decode(bv)
          queue <- ShortString.codec.decode(reserved1.remainder)
          consumerTag <- ShortString.codec.decode(queue.remainder)
          booleans1 <- MethodArgumentCodec.booleansDecoder(4).map(_.map(AmqpBoolean)).decode(consumerTag.remainder)
          Seq(noLocal, noAck, exclusive, noWait) = booleans1.value
          arguments <- Table.codec.decode(booleans1.remainder)
        } yield DecodeResult(
          Consume(reserved1.value, queue.value, consumerTag.value, noLocal, noAck, exclusive, noWait, arguments.value),
          arguments.remainder
        )
      }
      val codec: Codec[Consume] = Codec(encoder, decoder)
    }
 */
case class CodecGenerator(specReader: SpecReader) {

}
