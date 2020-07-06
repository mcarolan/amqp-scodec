package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.Tune
import net.mcarolan.amqpscodec.AmqpTypes._

object Tune {
    val ConnectionTuneEncoder: Encoder[Tune] =
      Encoder[Tune] { value: Tune =>
        for {
          channelMax <- AmqpShort.codec.encode(value.channelMax)
          frameMax <- AmqpLong.codec.encode(value.frameMax)
          heartbeat <- AmqpShort.codec.encode(value.heartbeat)
        } yield channelMax ++ frameMax ++ heartbeat
      }
}
