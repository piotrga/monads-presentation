package livedemo._03_monads

import akka.dispatch.Future
import akka.actor.ActorSystem


object _06_Future extends App{
  implicit val system = ActorSystem()

  // Futures
  val op1 : Future[Int] = for {
    a <- Future{ 10 * 10 }
    b <- Future{ a * a + 2 }
    c <- Future{ b * a }
  } yield a - b + c


}
