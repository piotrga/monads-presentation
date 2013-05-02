package livedemo._02_typeclasses

import me.prettyprint.hector.api.Keyspace
import cassandra.simpleoperations.blocking._
import livedemo.Person


object _01a_Save {

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

  val adam = Person("adam-123", "Adam", "adam@gmail.com", 34)
  save(adam)
}

