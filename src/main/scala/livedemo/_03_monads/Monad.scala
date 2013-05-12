package livedemo._03_monads

import me.prettyprint.hector.api.Keyspace

object Monad {

  implicit def FunctionToMonad[R, T](f: R => T) = new Monad[R, T] {
    def apply(r: R) = f(r)
  }

  trait Monad[R, A]{ self =>
    def apply(k: R): A
    def >>[B](action: Monad[R, B]) = flatMap(_ => action)
    def flatMap[B](f: A => Monad[R, B]) : Monad[R,B] = (k: R) => f(self(k))(k)
    def map[B](f:A=>B) : Monad[R, B] = (r: R) => f(self(r))
  }

  type CassandraMonad[A] = Monad[Keyspace, A]

  def Read[T](id:String) : CassandraMonad[Option[T]] = null
  def Save[T](o:T) : CassandraMonad[Unit] = null

}

object CassandraMonad{

  trait CassandraMonad[A]{ self =>
    def apply(k: Keyspace): A
    def unit[B](a:B) : CassandraMonad[B]
    def flatMap[B](f: A => CassandraMonad[B]) : CassandraMonad[B]

    def >>[B](action: CassandraMonad[B]) = flatMap(_ => action)
    def map[B](f:A=>B) : CassandraMonad[B] = flatMap(a => unit(f(a)))
  }

}