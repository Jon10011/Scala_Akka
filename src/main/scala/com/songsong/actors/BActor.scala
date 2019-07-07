package com.songsong.actors

import akka.actor.Actor

class BActor extends Actor{
  override def receive: Receive = {
    case "我打" => {
      println("BActor(乔峰) 挺猛 看我降龙十八掌")
      //sender()获取到发送Actor的代理/引用对象 ref
      sender() ! "我打"
    }
  }
}
