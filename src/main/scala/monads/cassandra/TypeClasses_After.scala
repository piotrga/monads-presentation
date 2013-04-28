package monads.cassandra

import livedemo.Person
import me.prettyprint.hector.api.Keyspace
import java.io.OutputStream
import me.prettyprint.hector.api.mutation.Mutator

object TypeClasses_After {
  implicit val keyspace: Keyspace = null

  trait CassandraObject[T] {
    def columnFamily: String
    def columns: Seq[String]
    def rowId(o: T): String
    def marshall(o: T): List[(String, String)]
    def unmarshall(f: Map[String,String]): Option[T]
  }

  implicit object PersonCO extends CassandraObject[Person] {
    def columnFamily = "people"

    def rowId(p: Person): String = { p.id }

    def marshall(p: Person): List[(String, String)] = List(
      "id" -> p.id,
      "name" -> p.name,
      "email" -> p.email,
      "age" -> p.age.toString
    )

    def unmarshall(f:Map[String,String]): Option[Person] = None
    def columns: Seq[String] = Nil

  }

  import simpleoperations.blocking._

  def Save[T](p: T)(implicit keyspace: Keyspace, pco: CassandraObject[T]) {
    put(pco.columnFamily, pco.rowId(p), pco.marshall(p): _*)
  }

  Save(Person("1234", "Adam", "a@b.com", 34))


  /* Serializable */

  trait Serializable[T] {
    def serialize(obj: T, out: OutputStream)
  }

  def writeToFile[T](obj: T, filename: String)(implicit serializer: Serializable[T]) = {}


//  Numeric
  def multiply[A](vector1 : List[A], vector2 : List[A])(implicit num: Numeric[A]): A = (for{
    e1 <- vector1
    e2 <- vector2
  } yield num.times(e1,e2)).sum


  multiply(List(1L, 2L),List(3L, 5L) )
  multiply(List(1.2, 2.3), List(3.4, 5.3) )
}
