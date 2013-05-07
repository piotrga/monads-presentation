package livedemo._03_monads

import akka.dispatch.Future
import akka.actor.ActorSystem


object _05_Option extends App{

  def readBalance(id : String) : Option[Double] = sys.error("Not implemented")

  val total : Option[Double] = for {
    balance1 <- readBalance("1")
    balance2 <- readBalance("2")
  } yield (balance1 + balance2)

}
