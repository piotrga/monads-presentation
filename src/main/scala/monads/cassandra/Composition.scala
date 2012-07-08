package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import monads.cassandra.Composition_After.ReaderMonad


object Composition {
  import crud.nonblocking._
  // Update[Person]("joe-123", _.copy(name = "Smith"))

  // val p = Read[Person]("joe-123").get
  // Save( p copy (name = "Smith") )

  trait Action[A]{ self =>
    def execute(k: Keyspace): A

    def chain[B](action: Action[B]): Action[B] = new Action[B] {
      def execute(k: Keyspace) = {
        self.execute(k)
        action.execute(k)
      }
    }


    def flatMap[B](f: A => Action[B]) : Action[B] = new Action[B] {
      def execute(k: Keyspace) = {
        val resA = self.execute(k)
        val action2 = f(resA)
        action2.execute(k)
      }
    }

    def map[B](f: A => B) : Action[B] = new Action[B] {
      def execute(k: Keyspace) = f(self.execute(k))
    }
  }

  case class Read(id:String) extends Action[Option[Person]]{
    def execute(k: Keyspace) = null
  }

  case class Save(p:Person)extends Action[Unit]{
    def execute(k: Keyspace) {}
  }

  def notFound: Nothing = { throw new RuntimeException("Not found") }

  Read("123").flatMap(p => Save(p.getOrElse(notFound).copy(name = "Smith")))



  val Update = for {
    p <- Read("123")
    _ <- Save(p.getOrElse(notFound).copy(name = "Smith"))
  } yield ()

  val keyspace: Keyspace = null

  Update.execute(keyspace)


}
