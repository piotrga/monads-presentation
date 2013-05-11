package livedemo._03_monads

import akka.dispatch.{Await, Future}
import akka.actor.ActorSystem
import akka.util.duration._

object _06_Future extends App{
  implicit val system = ActorSystem()

  def fetchFacebookFriends(): Future[Set[String]] = {
    Set()
  }
  def fetchTwitterFriends(): Future[Set[String]] = {
    Set()
  }
  def fetchCurrentFriends(): Future[Set[String]] = {
    Set()
  }

  def saveNewFriends : PartialFunction[Set[String], Unit] = {
    case nf => ()
  }

  val calculateNewFriends : Future[Set[String]] = for {
    facebookFriends <-  fetchFacebookFriends()
    twitterFriends <-  fetchTwitterFriends()
    currentFriends <-  fetchCurrentFriends()
  } yield (facebookFriends ++ twitterFriends) -- currentFriends

  calculateNewFriends onSuccess saveNewFriends

}
