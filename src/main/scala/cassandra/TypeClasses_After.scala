package cassandra

import me.prettyprint.hector.api.Keyspace
import java.io.OutputStream
import me.prettyprint.hector.api.mutation.Mutator
import livedemo.Person

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
}
