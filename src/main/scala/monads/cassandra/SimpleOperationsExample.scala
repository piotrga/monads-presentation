package monads.cassandra

import me.prettyprint.hector.api.Keyspace

object SimpleOperationsExample{
  import simpleoperations.blocking._

  implicit val keyspace = Cassandra.initKeyspace

  val joe = Get("people", "joe-123", "name", "age")
  assert ( joe("age") == "25")

}