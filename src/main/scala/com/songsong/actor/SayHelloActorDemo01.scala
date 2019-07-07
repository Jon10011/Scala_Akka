package com.songsong.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class SayHelloActorDemo01 extends Actor {
  override def receive: Receive = {
    case "hello" => println("收到hello，回应 hello too")
    case "ok" => println("收到ok ，回应ok too")
    case "exit" => {
      println("接受到exi指令，退出系统")
      context.stop(self)
      context.system.terminate()
    }
    case _ => println("没有匹配到消息")
  }
}

object SayHelloActor1 {
  private val actorFactory = ActorSystem("actorFactory")
  private val sayHelloActorRef: ActorRef = actorFactory.actorOf(Props[SayHelloActorDemo01], "sayHelloActor")

  def main(args: Array[String]): Unit = {
    sayHelloActorRef ! "hello"
    sayHelloActorRef ! "ok"
    sayHelloActorRef ! "haha"
    sayHelloActorRef ! "exit"
  }

}