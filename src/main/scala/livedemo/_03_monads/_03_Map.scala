package livedemo._03_monads

import livedemo.Person
import me.prettyprint.hector.api.Keyspace
import cassandra.TypeClasses_After.CassandraObject

object _03_Map {

  trait Action[A]{ self =>
    def apply(k: Keyspace): A
    def >>[B](action: Action[B]) = flatMap(_ => action)
    def flatMap[B](f: A => Action[B]) = new Action[B] {
      def apply(k: Keyspace) : B = f(self(k))(k)
    }
  }

  case class Get(columnFamilyId: String, rowId: String, columnNames: String*) extends Action[Map[String, String]]{
    def apply(k: Keyspace) : Map[String, String] = sys.error("not implemented")
  }

//  def Read[T](id:String)(implicit co: CassandraObject[T]) : Action[Option[T]]  =
//    Get(co.columnFamily, id, co.columns:_*) map co.unmarshall


}
