package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object Start {
    val ConnectionStartEncoder: Encoder[Connection.Start] =
      Encoder[Connection.Start] { value: Connection.Start =>
        for {
          versionMajor <- AmqpByte.codec.encode(value.versionMajor)
          versionMinor <- AmqpByte.codec.encode(value.versionMinor)
          serverProperties <- Table.codec.encode(value.serverProperties)
          mechanisms <- LongString.codec.encode(value.mechanisms)
          locales <- LongString.codec.encode(value.locales)
        } yield versionMajor ++ versionMinor ++ serverProperties ++ mechanisms ++ locales
      }

    val ConnectionStartDecoder: Decoder[Connection.Start] =
      Decoder[Connection.Start] { bv : BitVector =>
        for {
          versionMajor <- AmqpByte.codec.decode(bv)
          versionMinor <- AmqpByte.codec.decode(versionMajor.remainder)
          serverProperties <- Table.codec.decode(versionMinor.remainder)
          mechanisms <- LongString.codec.decode(serverProperties.remainder)
          locales <- LongString.codec.decode(mechanisms.remainder)
        } yield DecodeResult(
            Connection.Start(versionMajor.value, versionMinor.value, serverProperties.value, mechanisms.value, locales.value),
            locales.remainder
          )
      }
}
