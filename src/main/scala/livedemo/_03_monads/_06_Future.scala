package livedemo._03_monads

import akka.dispatch.{Await, Future}
import akka.actor.ActorSystem
import akka.util.duration._

object _06_Future extends App{
  implicit val system = ActorSystem()

  def fetchFacebookFriends(): Set[String] = {
    Set()
  }
  def fetchTwitterFriends(): Set[String] = {
    Set()
  }
  def fetchCurrentFriends(): Set[String] = {
    Set()
  }

  def saveNewFriends : PartialFunction[Set[String], Unit] = {
    case nf => ()
  }

  val newFriendsFuture : Future[Set[String]] = for {
    facebookFriends <- Future{ fetchFacebookFriends() }
    twitterFriends <- Future{ fetchTwitterFriends() }
    currentFriends <- Future{ fetchCurrentFriends() }
  } yield (facebookFriends ++ twitterFriends) -- currentFriends

  newFriendsFuture onSuccess saveNewFriends

}
