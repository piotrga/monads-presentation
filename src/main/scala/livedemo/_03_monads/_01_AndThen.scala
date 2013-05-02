package livedemo._03_monads

import me.prettyprint.hector.api.Keyspace
import livedemo.Person
import cassandra.TypeClasses_After.CassandraObject
import cassandra.simpleoperations


object _01_AndThen {

  trait Action[A]{ self =>
    def execute(k: Keyspace): A
  }

  case class Save[A](p:A) extends Action[Unit]{
    def execute(k: Keyspace) {/*...*/}
  }

  val joe = Person("1", "joe", "joe@gmail.com", 10)
  val jil = Person("2", "jil", "jil@gmail.com", 10)


//  val InsertTestUsers = Save(joe) andThen Save(jil)

}
