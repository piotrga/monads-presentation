package cassandra

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.duration._
import cassandra.crud.nonblocking._
import akka.util.Timeout
import akka.dispatch.Await
import me.prettyprint.hector.api.Keyspace
import cassandra.simpleoperations.nonblocking
import nonblocking._

object actors{
  implicit val cassandra : ActorRef  = Cassandra.actor
  implicit val timeout = Timeout(10 seconds)

  cassandra ! Put("PEOPLE", "jil-897", "name"->"Jil Blocks", "email"->"jil@blocks.com", "age"->"35")
//  cassandra ! Save(Person("joe-123", "Joe Smith", "joe@smith.com", 24))

//  cassandra ? Read[Person]("joe-123") onSuccess {
//    case Some(p:Person) => println("Just read [%s]" format p)
//  }
//
//  val joe = Await.result(cassandra ? Read[Person]("joe-123"), 10 seconds)
//
//
//  def blocking[T](f: Keyspace => T)(implicit cassandra: ActorRef, timeout: Timeout) : T = {
//    Await.result(cassandra ? f, timeout.duration).asInstanceOf[T]
//  }
//
//  val sue1  =  blocking( Read[Person]("sue-987") )
//  val sue2 : Option[Person] =  Read[Person]("sue-987")

}