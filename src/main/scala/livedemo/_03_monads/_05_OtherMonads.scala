package livedemo._03_monads

import akka.dispatch.Future
import akka.actor.ActorSystem


object _05_OtherMonads extends App{
  implicit val system = ActorSystem()

  // Futures
  val op1 : Future[Int] = for {
    a <- Future{ 10 * 10 }
    b <- Future{ a * a + 2 }
    c <- Future{ b * a }
  } yield a - b + c



  // Option
  def readBalance(id : String) : Option[Double] = sys.error("Not implemented")

  lazy val total : Option[Double] = for {
    balance1 <- readBalance("1")
    balance2 <- readBalance("2")
  } yield (balance1 + balance2)


  // Collections

  List(1,2,3,4).map(a => a*a) // List(1, 4, 9, 16)
  List(1,2,3).flatMap(a => List.fill(a)(a*a) ) // List(1, 4, 4, 9, 9, 9)

  // Implicit conversion: Option -> Iterable
  // 1 -> Some(100), 2 -> Some(200), 3 -> None
  List("1","2","3").flatMap(id => readBalance(id) ).sum // 300

}
