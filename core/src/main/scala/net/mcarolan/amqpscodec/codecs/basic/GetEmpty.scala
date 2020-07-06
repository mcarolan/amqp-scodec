package net.mcarolan.amqpscodec.codecs.basic

import scodec.Encoder
import net.mcarolan.amqpscodec.spec.Basic.GetEmpty
import net.mcarolan.amqpscodec.AmqpTypes._

object GetEmpty {
    val BasicGetEmptyEncoder: Encoder[GetEmpty] =
      Encoder[GetEmpty] { value: GetEmpty =>
        for {
          reserved1 <- ShortString.codec.encode(value.reserved1)
        } yield reserved1
      }
}
