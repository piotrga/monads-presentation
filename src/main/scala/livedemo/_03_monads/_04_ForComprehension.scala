package livedemo._03_monads

import me.prettyprint.hector.api.Keyspace
import cassandra.TypeClasses_After.CassandraObject

class _04_ForComprehension {
  import Monad._

  case class Account(id: String, balance : Double)

  def Total(id1: String, id2: String ) : Monad[Double] =
    Read[Account](id1) flatMap (a1 => Read[Account](id2) map ( a2 => a1.get.balance + a2.get.balance ))

  def Total2(id1: String, id2: String ) : Monad[Double] = for {
    a1 <- Read[Account](id1)
    a2 <- Read[Account](id2)
  } yield a1.get.balance + a2.get.balance

}
