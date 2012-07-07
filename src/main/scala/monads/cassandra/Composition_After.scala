package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import monads.cassandra.TypeClasses_After.CassandraObject

object Composition_After {

  implicit val keyspace: Keyspace = null
  // Update[Person]("joe-123", _.copy(name = "Smith"))
  // val p = Read[Person]("joe-123").get
  // Save( p copy (name = "Smith") )

  trait Action[A] {
    self =>
    def execute(k: Keyspace): A

    def >>[B](action2: Action[B]): Action[B] = {
      new Action[B] {
        def execute(k: Keyspace): B = {
          self.execute(k)
          action2.execute(k)
        }
      }
    }

    def flatMap[B](f: A => Action[B]): Action[B] = {
      new Action[B] {
        def execute(k: Keyspace) = {
          val resA = self.execute(k)
          val action2 = f(resA)
          action2.execute(k)
        }
      }
    }

    def map[B](f: A => B): Action[B] = {
      new Action[B] {
        def execute(k: Keyspace) = f(self.execute(k))
      }
    }

  }

  implicit def fun2Reader[C, A](f: C => A): ReaderMonad[C, A] = new ReaderMonad[C, A] {
    def apply(k: C) = f(k)
  }


  trait ReaderMonad[C, A] extends Function[C, A] { self =>
    def apply(k: C): A
    def >>[B](action2: ReaderMonad[C, B]): ReaderMonad[C, B] = flatMap(_ => action2)
    def flatMap[B](f: A => ReaderMonad[C, B]): ReaderMonad[C, B] = (k: C) => f(self(k))(k)
    def map[B](f: A => B): ReaderMonad[C, B] = (k: C) => f(self(k))
    def withFilter(p : A=> Boolean) :ReaderMonad[C, A] = null
  }

  import crud.nonblocking._
  import TypeClasses_After.PersonCO

  val update = Read[Person]("123") flatMap { maybePerson => Save(maybePerson.get copy (name = "Smith") ) }
  update(keyspace)

  def Update[T: CassandraObject](id: String, f: T => T) = for {
    maybePerson <- Read[T](id)
    _ <- Save(f(maybePerson.get)) if maybePerson.isDefined
  } yield maybePerson.isDefined


  Update[Person]("123", _.copy(name = "Smith"))


}


