package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.Start
import net.mcarolan.amqpscodec.AmqpTypes._

object Start {
    val ConnectionStartEncoder: Encoder[Start] =
      Encoder[Start] { value: Start =>
        for {
          versionMajor <- AmqpByte.codec.encode(value.versionMajor)
          versionMinor <- AmqpByte.codec.encode(value.versionMinor)
          serverProperties <- Table.codec.encode(value.serverProperties)
          mechanisms <- LongString.codec.encode(value.mechanisms)
          locales <- LongString.codec.encode(value.locales)
        } yield versionMajor ++ versionMinor ++ serverProperties ++ mechanisms ++ locales
      }
}
