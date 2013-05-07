package livedemo._02_typeclasses

import me.prettyprint.hector.api.Keyspace
import cassandra.simpleoperations.blocking._
import scala.Some
import livedemo.Person

object _01b_Read{
  implicit val keyspace: Keyspace = null

  trait CassandraObject[T]{
    def marshall(p: T): List[(String, String)]
    def rowId(p: T): String
    def columnFamily: String
  }
  implicit object PersonCO extends CassandraObject[Person]{
    def marshall(p: Person): List[(String, String)] = List(
      "id" -> p.id,
      "name" -> p.name,
      "email" -> p.email,
      "age" -> p.age.toString
    )

    def rowId(p: Person): String = p.id
    def columnFamily: String = "people"
  }


  def read(id:String)(implicit keyspace: Keyspace): Option[Person] = {

    val row = get("people", id, "id", "name", "email", "age")
    if (!row.isEmpty)
      Some(Person(row("id"), row("name"), row("email"), row("age").toInt))
    else
      None
  }

  read("adam-123")


}
