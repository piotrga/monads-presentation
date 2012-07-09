package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import me.prettyprint.hector.api.mutation.Mutator
import java.io.{FileOutputStream, OutputStream, Externalizable, File}

case class Person(id: String, name: String, email: String, age: Int)

object TypeClasses {
  import simpleoperations.blocking._

  implicit val keyspace: Keyspace = null

  def Save(p: Person)(implicit keyspace: Keyspace) {
    Put("people", p.id,
      "id" -> p.id,
      "name" -> p.name,
      "email" -> p.email,
      "age" -> p.age.toString
    )
  }
}