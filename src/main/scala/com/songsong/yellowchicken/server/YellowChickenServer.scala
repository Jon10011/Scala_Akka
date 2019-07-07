package com.songsong.yellowchicken.server

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.songsong.yellowchicken.common.{ClientMessage, ServerMessage}
import com.typesafe.config.ConfigFactory

class YellowChickenServer extends Actor {
  override def receive: Receive = {
    case "start" => println("小黄鸡客服开始工作了")
    //如果接收到ClientMessage
    case ClientMessage(mes) => {
      //使用match --case 匹配(模糊)
      mes match {
        case "大数据学费" => sender() ! ServerMessage("35000RMB")
        case "学校地址" => sender() ! ServerMessage("北京昌平xx路xx大楼")
        case "学习什么技术" => sender() ! ServerMessage("大数据 前端 python")
        case "再见" => sender() ! ServerMessage("接收到退出指令，结束咨询")
        case _ => sender() ! ServerMessage("你说的啥子~")
      }
    }
  }
}

//主程序
object YellowChickenServer extends App {
  val serverHost = "192.168.1.102" //
  val serverPort = 9999
  //创建config对象指定类型协议，监听的ip和端口
  val config = ConfigFactory.parseString(
    s"""
       |akka.actor.provider="akka.remote.RemoteActorRefProvider"
       |akka.remote.ntty.tcp.hostname=$serverHost
       |akka.remote.netty.tcp.port=$serverPort
    """.stripMargin
  )

  //创建ActorSystem
  //url--统一资源定位
  val serverActorSystem = ActorSystem("Server", config)
  val yellowChickenServerRef: ActorRef = serverActorSystem.actorOf(Props[YellowChickenServer], "YellowChickenServer")
  yellowChickenServerRef ! "start"
}