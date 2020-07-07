package net.mcarolan.amqpscodec.codecs.connection

import scodec.{Encoder, Decoder, DecodeResult}
import net.mcarolan.amqpscodec.spec.Connection
import net.mcarolan.amqpscodec.AmqpTypes._
import scodec.bits.BitVector

object Tune {
    val ConnectionTuneEncoder: Encoder[Connection.Tune] =
      Encoder[Connection.Tune] { value: Connection.Tune =>
        for {
          channelMax <- AmqpShort.codec.encode(value.channelMax)
          frameMax <- AmqpLong.codec.encode(value.frameMax)
          heartbeat <- AmqpShort.codec.encode(value.heartbeat)
        } yield channelMax ++ frameMax ++ heartbeat
      }

    val ConnectionTuneDecoder: Decoder[Connection.Tune] =
      Decoder[Connection.Tune] { bv : BitVector =>
        for {
          channelMax <- AmqpShort.codec.decode(bv)
          frameMax <- AmqpLong.codec.decode(channelMax.remainder)
          heartbeat <- AmqpShort.codec.decode(frameMax.remainder)
        } yield DecodeResult(
            Connection.Tune(channelMax.value, frameMax.value, heartbeat.value),
            heartbeat.remainder
          )
      }
}
