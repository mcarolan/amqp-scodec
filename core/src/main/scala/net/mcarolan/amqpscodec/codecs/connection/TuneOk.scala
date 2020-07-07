package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object TuneOk {
    val ConnectionTuneOkEncoder: Encoder[Connection.TuneOk] =
      Encoder[Connection.TuneOk] { value: Connection.TuneOk =>
        for {
          channelMax <- AmqpShort.codec.encode(value.channelMax)
          frameMax <- AmqpLong.codec.encode(value.frameMax)
          heartbeat <- AmqpShort.codec.encode(value.heartbeat)
        } yield channelMax ++ frameMax ++ heartbeat
      }

    val ConnectionTuneOkDecoder: Decoder[Connection.TuneOk] =
      Decoder[Connection.TuneOk] { bv : BitVector =>
        for {
          channelMax <- AmqpShort.codec.decode(bv)
          frameMax <- AmqpLong.codec.decode(channelMax.remainder)
          heartbeat <- AmqpShort.codec.decode(frameMax.remainder)
        } yield DecodeResult(
            Connection.TuneOk(channelMax.value, frameMax.value, heartbeat.value),
            heartbeat.remainder
          )
      }
}
