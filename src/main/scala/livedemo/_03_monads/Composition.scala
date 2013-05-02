package livedemo._03_monads

import me.prettyprint.hector.api.Keyspace
import cassandra.Cassandra
import livedemo.Person


object Composition {
  val keyspace: Keyspace = Cassandra.initKeyspace

  val joe = Person("1", "joe", "joe@gmail.com", 10)
  val jil = Person("2", "jil", "jil@gmail.com", 10)

  trait Action[A]{ self =>
    def execute(k: Keyspace): A
  }

//  val DelteAllPeople : Action[Unit] = null
//  val InsertNewPeope : Action[Unit] = null
//
//  val refreshUsers = DelteAllPeople chain InsertNewPeope

  case class Read[A](id:String) extends Action[Option[A]]{
    def execute(k: Keyspace) : Option[A] = null
  }

  case class Save[A](p:A)extends Action[Unit]{
    def execute(k: Keyspace) {}
  }


}
