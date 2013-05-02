package livedemo._03_monads

import me.prettyprint.hector.api.Keyspace

object Monad {
  trait Monad[A]{ self =>
    def execute(k: Keyspace): A
    def >>[B](action: Monad[B]) = flatMap(_ => action)
    def flatMap[B](f: A => Monad[B]) = new Monad[B] {
      def execute(k: Keyspace) : B = f(self.execute(k)).execute(k)
    }
    def map[B](f:A=>B) : Monad[B] = new Monad[B] {
      def execute(k: Keyspace) = f(self.execute(k))
    }
  }

  def Read[T](id:String) : Monad[Option[T]] = null
  def Save[T](o:T) : Monad[Unit] = null

}
