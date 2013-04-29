package livedemo.typeclasses

import me.prettyprint.hector.api.Keyspace
import cassandra.simpleoperations.blocking._

case class Person(id: String, name: String, email: String, age: Int)

object _01_Save {

  implicit val keyspace: Keyspace = null

  def save(p: Person)
          (implicit keyspace: Keyspace) {
    put("people", p.id,
      "id" -> p.id,
      "name" -> p.name,
      "email" -> p.email,
      "age" -> p.age.toString
    )
  }

  def read(id:String)
          (implicit keyspace: Keyspace): Option[Person] = {
    val d = get("people", id, "id", "name", "email", "age").toMap
    if (d.isEmpty)
      None
    else
      Some(Person(d("id"), d("name"), d("email"), d("age").toInt))
  }

  val adam = Person("adam-123", "Adam", "adam@gmail.com", 34)
  save(adam)
  read("adam-123")
}