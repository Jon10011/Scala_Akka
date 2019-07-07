package com.songsong.actors

import akka.actor.Actor

class BActor extends Actor{
  override def receive: Receive = {
    case "我打" => {
      println("BActor(乔峰) 挺猛 看我降龙十八掌")
      sender() ! "我打"
    }
  }
}
