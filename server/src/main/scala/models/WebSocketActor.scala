package models

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import d2spa.shared._
import d2spa.shared.WebSocketMessages._
import models.EOModelActor.{EOModelResponse, GetEOModel}
import models.EORepoActor.{DeletingResponse, FetchedObjects, FetchedObjectsForList, SavingResponse}
import models.MenusActor.{GetMenus, MenusResponse}
import models.NodeActor.SetItUp
import models.RulesActor.{GetRule, GetRulesForMetaData, RuleResultsResponse}

import scala.concurrent.duration._
import scala.concurrent._
import ExecutionContext.Implicits.global


class WebSocketActor(out: ActorRef, nodeActor: ActorRef) extends Actor {
  val config = ConfigFactory.load()
  val showDebugButton = if (config.getIsNull("d2spa.showDebugButton")) true else config.getBoolean("d2spa.showDebugButton")


  /*val eomodelActor = context.actorOf(EOModelActor.props(), "eomodelFetcher")
val menusActor = context.actorOf(MenusActor.props(eomodelActor), "menusFetcher")
val rulesActor = context.actorOf(RulesActor.props(eomodelActor), "rulesFetcher")
val eoRepoActor = context.actorOf(EORepoActor.props(eomodelActor), "eoRepo")*/

  override def preStart: Unit = {
    nodeActor ! SetItUp
  }


  def receive = {


    case msg: WebSocketMsgIn => msg match {

      case GetDebugConfiguration =>
        println("Receive GetDebugConfiguration ---> sending DebugConfMsg")
        out ! DebugConfMsg(showDebugButton)


    }
  }
}

object WebSocketActor {
  def props(out: ActorRef, nodeActor: ActorRef): Props = Props(new WebSocketActor(out, nodeActor))
}
