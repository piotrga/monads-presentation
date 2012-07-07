package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import java.io.OutputStream
import me.prettyprint.hector.api.mutation.Mutator

object TypeClasses_After {
  implicit val keyspace: Keyspace = null
  implicit val mutator: Mutator[String] = null

  trait CassandraObject[T] {
    def columnFamily: String

    def rowId(o: T): String

    def marshall(o: T): List[(String, String)]
  }

  implicit object PersonCO extends CassandraObject[Person] {
    def columnFamily = "people"

    def rowId(p: Person): String = {
      p.id
    }

    def marshall(p: Person): List[(String, String)] = List(
      "id" -> p.id,
      "name" -> p.name,
      "email" -> p.email,
      "age" -> p.age.toString
    )
  }

  import simpleoperations.blocking._

  def Save[T](p: T)(implicit keyspace: Keyspace, pco: CassandraObject[T]) {
    Put(pco.columnFamily, pco.rowId(p), pco.marshall(p): _*)
  }

  Save(Person("1234", "Adam", "a@b.com", 34))


  trait Serializable[T] {
    def serialize(obj: T, out: OutputStream)
  }

  def writeToFile[T](obj: T, filename: String)(implicit serializer: Serializable[T]) = {}
}
