package cassandra

import me.prettyprint.hector.api.Keyspace

object SimpleOperationsExample{
  import simpleoperations.blocking._

  implicit val keyspace = Cassandra.initKeyspace

  val joe = get("people", "joe-123", "name", "age")
  assert ( joe("age") == "25")

}