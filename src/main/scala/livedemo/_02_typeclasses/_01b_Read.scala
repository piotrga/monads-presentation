package livedemo._02_typeclasses

import me.prettyprint.hector.api.Keyspace
import cassandra.simpleoperations.blocking._
import scala.Some
import livedemo.Person

object _01b_Read{
  implicit val keyspace: Keyspace = null

  def read(id:String)
          (implicit keyspace: Keyspace): Option[Person] = {
    val row = get("people", id, "id", "name", "email", "age")
    if (!row.isEmpty)
      Some(Person(row("id"), row("name"), row("email"), row("age").toInt))
    else
      None
  }

  read("adam-123")


}
