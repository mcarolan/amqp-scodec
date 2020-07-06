package net.mcarolan.amqpscodec.codecs.connection

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Connection.TuneOk
import net.mcarolan.amqpscodec.AmqpTypes._

object TuneOk {
    val ConnectionTuneOkEncoder: Encoder[TuneOk] =
      Encoder[TuneOk] { value: TuneOk =>
        for {
          channelMax <- AmqpShort.codec.encode(value.channelMax)
          frameMax <- AmqpLong.codec.encode(value.frameMax)
          heartbeat <- AmqpShort.codec.encode(value.heartbeat)
        } yield channelMax ++ frameMax ++ heartbeat
      }
}
