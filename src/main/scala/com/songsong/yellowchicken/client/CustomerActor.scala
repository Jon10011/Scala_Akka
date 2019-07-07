package com.songsong.yellowchicken.client

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.songsong.yellowchicken.common.{ClientMessage, ServerMessage}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

class CustomerActor(serverHost: String, serverPort: Int) extends Actor {
  //定义一个YellowChickenServerRef
  var serverActorRef: ActorSelection = _

  //在Actor中又一个方法preStart方法，会在actor运行前执行
  //在akka的开发中。通常将初始化的工作放在preStart中
  override def preStart(): Unit = {
    println("preStart() 执行了")
    serverActorRef = context.actorSelection(s"akka.tcp://Server@${serverHost}:${serverPort}/user/YellowChickenServer")
    println(s"serverActorRef=$serverActorRef")
  }

  override def receive: Receive = {
    case "start" => println("客户端运行了，可以开始咨询问腿")
    case "再见" => {
      println("接受到退出指令，结束咨询")
      context.stop(self)
      context.system.terminate()
    }
    case mes: String => {
      //发送到服务端
      serverActorRef ! ClientMessage(mes) //使用ClientMessage case class apply方法
      //如果接收到服务器的回复
    }
    case ServerMessage(mes) => {
      println(s"收到小黄鸡客服(Server): $mes")
      println("please imput your thing")
    }
  }
}

object CustomerActor extends App {

  val (clientHost, clientPost, serverHost, serverPort) = ("192.168.1.102", 9990, "192.168.1.102", 9999)
  //创建config对象指定类型协议，监听的ip和端口
  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.ntty.tcp.hostname=$clientHost
       |akka.remote.netty.tcp.port=$clientPost
    """.stripMargin)
  //创建ActorSystem
  val clientActorSystem = ActorSystem("client", config)

  //实例和引用
  val customerActorRef: ActorRef = clientActorSystem.actorOf(Props(new CustomerActor(serverHost, serverPort)), "CustomerActor")
  //启动 customerActorRef
  customerActorRef ! "start"

  //发送消息
  while (true) {
    val mes1 = StdIn.readLine()
    customerActorRef ! mes1
  }

}