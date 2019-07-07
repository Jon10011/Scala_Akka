package com.songsong.actors

import akka.actor.{ActorRef, ActorSystem, Props}

object ActorGame extends App {
  //创建actorfactory
  val actorfactory = ActorSystem("actorfactory")
  //创建BActor的引用或代理
  val bActorRef: ActorRef = actorfactory.actorOf(Props[BActor], "bActor")
  //创建AActor的引用或代理
  val aActorRef: ActorRef = actorfactory.actorOf(Props(new AActor(bActorRef)), "aActor")

  aActorRef ! "start"
}
