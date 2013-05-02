package livedemo._03_monads

import me.prettyprint.hector.api.Keyspace
import livedemo.Person

object _02_FlatMap {
  import _01_AndThen.Save

  trait Action[A]{ self =>
    def execute(k: Keyspace): A

    def andThen[B](action: Action[B]) = new Action[B] {
      def execute(k: Keyspace) = {
        self.execute(k)
        action.execute(k)
      }
    }
  }

  case class Read[T](id:String) extends Action[Option[T]]  {
    def execute(k: Keyspace) : Option[T] = sys.error("not implemented")
  }

  val joe = Person("123", "Baker", "joe.baker@gmail.com", 10)


//  val Update = Read[Person]("123") andThen2 ( p => Save( p.get copy (name = "Smith") ) )



}
