package net.mcarolan.amqpscodec

import net.mcarolan.amqpscodec.AmqpTypes._

package object spec {

  sealed trait AmqpMethod
  
  case object Connection {
    val index: AmqpShort = AmqpShort(10)
    sealed trait Method extends AmqpMethod
    
    case class Start(
      versionMajor: AmqpByte,
      versionMinor: AmqpByte,
      serverProperties: Table,
      mechanisms: LongString,
      locales: LongString
    ) extends Method
    object Start {
      val index: AmqpShort = AmqpShort(10)
    }

    case class StartOk(
      clientProperties: Table,
      mechanism: ShortString,
      response: LongString,
      locale: ShortString
    ) extends Method
    object StartOk {
      val index: AmqpShort = AmqpShort(11)
    }

    case class Secure(
      challenge: LongString
    ) extends Method
    object Secure {
      val index: AmqpShort = AmqpShort(20)
    }

    case class SecureOk(
      response: LongString
    ) extends Method
    object SecureOk {
      val index: AmqpShort = AmqpShort(21)
    }

    case class Tune(
      channelMax: AmqpShort,
      frameMax: AmqpLong,
      heartbeat: AmqpShort
    ) extends Method
    object Tune {
      val index: AmqpShort = AmqpShort(30)
    }

    case class TuneOk(
      channelMax: AmqpShort,
      frameMax: AmqpLong,
      heartbeat: AmqpShort
    ) extends Method
    object TuneOk {
      val index: AmqpShort = AmqpShort(31)
    }

    case class Open(
      virtualHost: ShortString,
      reserved1: ShortString,
      reserved2: AmqpBoolean
    ) extends Method
    object Open {
      val index: AmqpShort = AmqpShort(40)
    }

    case class OpenOk(
      reserved1: ShortString
    ) extends Method
    object OpenOk {
      val index: AmqpShort = AmqpShort(41)
    }

    case class Close(
      replyCode: AmqpShort,
      replyText: ShortString,
      classId: AmqpShort,
      methodId: AmqpShort
    ) extends Method
    object Close {
      val index: AmqpShort = AmqpShort(50)
    }

    case class CloseOk(

    ) extends Method
    object CloseOk {
      val index: AmqpShort = AmqpShort(51)
    }

  }

  case object Channel {
    val index: AmqpShort = AmqpShort(20)
    sealed trait Method extends AmqpMethod
    
    case class Open(
      reserved1: ShortString
    ) extends Method
    object Open {
      val index: AmqpShort = AmqpShort(10)
    }

    case class OpenOk(
      reserved1: LongString
    ) extends Method
    object OpenOk {
      val index: AmqpShort = AmqpShort(11)
    }

    case class Flow(
      active: AmqpBoolean
    ) extends Method
    object Flow {
      val index: AmqpShort = AmqpShort(20)
    }

    case class FlowOk(
      active: AmqpBoolean
    ) extends Method
    object FlowOk {
      val index: AmqpShort = AmqpShort(21)
    }

    case class Close(
      replyCode: AmqpShort,
      replyText: ShortString,
      classId: AmqpShort,
      methodId: AmqpShort
    ) extends Method
    object Close {
      val index: AmqpShort = AmqpShort(40)
    }

    case class CloseOk(

    ) extends Method
    object CloseOk {
      val index: AmqpShort = AmqpShort(41)
    }

  }

  case object Exchange {
    val index: AmqpShort = AmqpShort(40)
    sealed trait Method extends AmqpMethod
    
    case class Declare(
      reserved1: AmqpShort,
      exchange: ShortString,
      `type`: ShortString,
      passive: AmqpBoolean,
      durable: AmqpBoolean,
      reserved2: AmqpBoolean,
      reserved3: AmqpBoolean,
      noWait: AmqpBoolean,
      arguments: Table
    ) extends Method
    object Declare {
      val index: AmqpShort = AmqpShort(10)
    }

    case class DeclareOk(

    ) extends Method
    object DeclareOk {
      val index: AmqpShort = AmqpShort(11)
    }

    case class Delete(
      reserved1: AmqpShort,
      exchange: ShortString,
      ifUnused: AmqpBoolean,
      noWait: AmqpBoolean
    ) extends Method
    object Delete {
      val index: AmqpShort = AmqpShort(20)
    }

    case class DeleteOk(

    ) extends Method
    object DeleteOk {
      val index: AmqpShort = AmqpShort(21)
    }

  }

  case object Queue {
    val index: AmqpShort = AmqpShort(50)
    sealed trait Method extends AmqpMethod
    
    case class Declare(
      reserved1: AmqpShort,
      queue: ShortString,
      passive: AmqpBoolean,
      durable: AmqpBoolean,
      exclusive: AmqpBoolean,
      autoDelete: AmqpBoolean,
      noWait: AmqpBoolean,
      arguments: Table
    ) extends Method
    object Declare {
      val index: AmqpShort = AmqpShort(10)
    }

    case class DeclareOk(
      queue: ShortString,
      messageCount: AmqpLong,
      consumerCount: AmqpLong
    ) extends Method
    object DeclareOk {
      val index: AmqpShort = AmqpShort(11)
    }

    case class Bind(
      reserved1: AmqpShort,
      queue: ShortString,
      exchange: ShortString,
      routingKey: ShortString,
      noWait: AmqpBoolean,
      arguments: Table
    ) extends Method
    object Bind {
      val index: AmqpShort = AmqpShort(20)
    }

    case class BindOk(

    ) extends Method
    object BindOk {
      val index: AmqpShort = AmqpShort(21)
    }

    case class Unbind(
      reserved1: AmqpShort,
      queue: ShortString,
      exchange: ShortString,
      routingKey: ShortString,
      arguments: Table
    ) extends Method
    object Unbind {
      val index: AmqpShort = AmqpShort(50)
    }

    case class UnbindOk(

    ) extends Method
    object UnbindOk {
      val index: AmqpShort = AmqpShort(51)
    }

    case class Purge(
      reserved1: AmqpShort,
      queue: ShortString,
      noWait: AmqpBoolean
    ) extends Method
    object Purge {
      val index: AmqpShort = AmqpShort(30)
    }

    case class PurgeOk(
      messageCount: AmqpLong
    ) extends Method
    object PurgeOk {
      val index: AmqpShort = AmqpShort(31)
    }

    case class Delete(
      reserved1: AmqpShort,
      queue: ShortString,
      ifUnused: AmqpBoolean,
      ifEmpty: AmqpBoolean,
      noWait: AmqpBoolean
    ) extends Method
    object Delete {
      val index: AmqpShort = AmqpShort(40)
    }

    case class DeleteOk(
      messageCount: AmqpLong
    ) extends Method
    object DeleteOk {
      val index: AmqpShort = AmqpShort(41)
    }

  }

  case object Basic {
    val index: AmqpShort = AmqpShort(60)
    sealed trait Method extends AmqpMethod
    
    case class Qos(
      prefetchSize: AmqpLong,
      prefetchCount: AmqpShort,
      global: AmqpBoolean
    ) extends Method
    object Qos {
      val index: AmqpShort = AmqpShort(10)
    }

    case class QosOk(

    ) extends Method
    object QosOk {
      val index: AmqpShort = AmqpShort(11)
    }

    case class Consume(
      reserved1: AmqpShort,
      queue: ShortString,
      consumerTag: ShortString,
      noLocal: AmqpBoolean,
      noAck: AmqpBoolean,
      exclusive: AmqpBoolean,
      noWait: AmqpBoolean,
      arguments: Table
    ) extends Method
    object Consume {
      val index: AmqpShort = AmqpShort(20)
    }

    case class ConsumeOk(
      consumerTag: ShortString
    ) extends Method
    object ConsumeOk {
      val index: AmqpShort = AmqpShort(21)
    }

    case class Cancel(
      consumerTag: ShortString,
      noWait: AmqpBoolean
    ) extends Method
    object Cancel {
      val index: AmqpShort = AmqpShort(30)
    }

    case class CancelOk(
      consumerTag: ShortString
    ) extends Method
    object CancelOk {
      val index: AmqpShort = AmqpShort(31)
    }

    case class Publish(
      reserved1: AmqpShort,
      exchange: ShortString,
      routingKey: ShortString,
      mandatory: AmqpBoolean,
      immediate: AmqpBoolean
    ) extends Method
    object Publish {
      val index: AmqpShort = AmqpShort(40)
    }

    case class Return(
      replyCode: AmqpShort,
      replyText: ShortString,
      exchange: ShortString,
      routingKey: ShortString
    ) extends Method
    object Return {
      val index: AmqpShort = AmqpShort(50)
    }

    case class Deliver(
      consumerTag: ShortString,
      deliveryTag: AmqpLongLong,
      redelivered: AmqpBoolean,
      exchange: ShortString,
      routingKey: ShortString
    ) extends Method
    object Deliver {
      val index: AmqpShort = AmqpShort(60)
    }

    case class Get(
      reserved1: AmqpShort,
      queue: ShortString,
      noAck: AmqpBoolean
    ) extends Method
    object Get {
      val index: AmqpShort = AmqpShort(70)
    }

    case class GetOk(
      deliveryTag: AmqpLongLong,
      redelivered: AmqpBoolean,
      exchange: ShortString,
      routingKey: ShortString,
      messageCount: AmqpLong
    ) extends Method
    object GetOk {
      val index: AmqpShort = AmqpShort(71)
    }

    case class GetEmpty(
      reserved1: ShortString
    ) extends Method
    object GetEmpty {
      val index: AmqpShort = AmqpShort(72)
    }

    case class Ack(
      deliveryTag: AmqpLongLong,
      multiple: AmqpBoolean
    ) extends Method
    object Ack {
      val index: AmqpShort = AmqpShort(80)
    }

    case class Reject(
      deliveryTag: AmqpLongLong,
      requeue: AmqpBoolean
    ) extends Method
    object Reject {
      val index: AmqpShort = AmqpShort(90)
    }

    case class RecoverAsync(
      requeue: AmqpBoolean
    ) extends Method
    object RecoverAsync {
      val index: AmqpShort = AmqpShort(100)
    }

    case class Recover(
      requeue: AmqpBoolean
    ) extends Method
    object Recover {
      val index: AmqpShort = AmqpShort(110)
    }

    case class RecoverOk(

    ) extends Method
    object RecoverOk {
      val index: AmqpShort = AmqpShort(111)
    }

  }

  case object Tx {
    val index: AmqpShort = AmqpShort(90)
    sealed trait Method extends AmqpMethod
    
    case class Select(

    ) extends Method
    object Select {
      val index: AmqpShort = AmqpShort(10)
    }

    case class SelectOk(

    ) extends Method
    object SelectOk {
      val index: AmqpShort = AmqpShort(11)
    }

    case class Commit(

    ) extends Method
    object Commit {
      val index: AmqpShort = AmqpShort(20)
    }

    case class CommitOk(

    ) extends Method
    object CommitOk {
      val index: AmqpShort = AmqpShort(21)
    }

    case class Rollback(

    ) extends Method
    object Rollback {
      val index: AmqpShort = AmqpShort(30)
    }

    case class RollbackOk(

    ) extends Method
    object RollbackOk {
      val index: AmqpShort = AmqpShort(31)
    }

  }

}
