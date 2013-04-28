package livedemo.typeclasses

import me.prettyprint.hector.api.Keyspace
import cassandra.simpleoperations.blocking._

case class Person(id: String, name: String, email: String, age: Int)

object _01_Save {

  implicit val keyspace: Keyspace = null

  def save(p: Person)(implicit keyspace: Keyspace) {
    put("people", p.id,
      "id" -> p.id,
      "name" -> p.name,
      "email" -> p.email,
      "age" -> p.age.toString
    )
  }

  val adam = Person("adam-123", "Adam", "adam@gmail.com", 34)
  save(adam)
}