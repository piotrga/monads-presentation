package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import monads.cassandra.Composition_After.ReaderMonad
import monads.cassandra.TypeClasses_After.CassandraObject


object Composition {
  val keyspace: Keyspace = Cassandra.initKeyspace

  val joe = Person("1", "joe", "joe@gmail.com", 10)
  val jil = Person("2", "jil", "jil@gmail.com", 10)

  trait Action[A]{ self =>
    def execute(k: Keyspace): A
  }

//  val saveBoth = Save(joe) chain Save(jil)
//  val saveBoth2 = Save(joe) chain Save(jil)
//  saveBoth chain saveBoth2

  case class Read[A](id:String) extends Action[A]{
    def execute(k: Keyspace) : Option[A] = null
  }

  case class Save[A](p:A)extends Action[Unit]{
    def execute(k: Keyspace) {}
  }


}
