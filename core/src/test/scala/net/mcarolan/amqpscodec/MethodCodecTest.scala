package net.mcarolan.amqpscodec

import cats.data.NonEmptyList
import scodec.bits.BitVector
import org.scalatest.matchers.should.Matchers._
import scodec.{Attempt, DecodeResult}

class MethodCodecTest extends org.scalatest.FunSuite {

  test("Encode 1 bit") {
    val input = NonEmptyList.of(true)
    val expected =
      BitVector.bits(Seq(false, false, false, false, false, false, false, true))

    MethodArgumentCodec.booleansEncoder.encode(input) shouldBe Attempt.successful(expected)
  }


  test("Encode 2 bits") {
    val input = NonEmptyList.of(true, true)
    val expected =
      BitVector.bits(Seq(false, false, false, false, false, false, true, true))

    MethodArgumentCodec.booleansEncoder.encode(input) shouldBe Attempt.successful(expected)
  }

  test("Encode 8 bits") {
    val input = NonEmptyList.of(true, true, true, true, true, true, true, true)
    val expected =
      BitVector.bits(Seq(true, true, true, true, true, true, true, true))

    MethodArgumentCodec.booleansEncoder.encode(input) shouldBe Attempt.successful(expected)
  }


  test("Encode 9 bits") {
    val input = NonEmptyList.fromListUnsafe((1 to 9).map(_ => true).toList)
    val expected =
      BitVector.bits(Seq(
        false, false, false, false, false, false, false, true,
        true, true, true, true, true, true, true, true
      ))

    MethodArgumentCodec.booleansEncoder.encode(input) shouldBe Attempt.successful(expected)
  }


  test("Decode 1 bit") {
    val expected = Seq(true)
    val input =
      BitVector.bits(Seq(false, false, false, false, false, false, false, true))

    MethodArgumentCodec.booleansDecoder(1).decode(input) shouldBe Attempt.Successful(DecodeResult(expected, BitVector.empty))
  }


  test("Decode 2 bits") {
    val expected = Seq(true, true)
    val input =
      BitVector.bits(Seq(false, false, false, false, false, false, true, true))

    MethodArgumentCodec.booleansDecoder(2).decode(input) shouldBe Attempt.successful(DecodeResult(expected, BitVector.empty))
  }

  test("Decode 8 bits") {
    val expected = Seq(true, true, true, true, true, true, true, true)
    val input =
      BitVector.bits(Seq(true, true, true, true, true, true, true, true))

    MethodArgumentCodec.booleansDecoder(8).decode(input) shouldBe Attempt.successful(DecodeResult(expected, BitVector.empty))
  }

  test("Decode 9 bits") {
    val expected = (1 to 9).map(_ => true)
    val input =
      BitVector.bits(Seq(
        false, false, false, false, false, false, false, true,
        true, true, true, true, true, true, true, true
      ))

    MethodArgumentCodec.booleansDecoder(9).decode(input) shouldBe Attempt.successful(DecodeResult(expected, BitVector.empty))
  }


}
