package livedemo._03_monads

class _03a_MonadDefinition {

  trait Monad[A]{
    def unit[B](a: B) : Monad[B]
    def flatMap[B](f: A => Monad[B]) : Monad[B]
  }

  val x,y,z : Monad[_] = _

  //identity
  x.flatMap(a => x.unit(a)) == x

  //associativity
  x.flatMap(a => y.flatMap(b => z)) == x.flatMap(a => y).flatMap(b => z)


}
