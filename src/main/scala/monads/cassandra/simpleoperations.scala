package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import me.prettyprint.hector.api.query.SliceQuery
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.cassandra.serializers.StringSerializer
import scala.collection.JavaConverters._
import me.prettyprint.hector.api.mutation.Mutator

object simpleoperations {

  object nonblocking {

    def Get(columnFamilyId: String, rowId: String, columnNames: String*) : Keyspace => Map[String, String] =
      keyspace => {
        val query: SliceQuery[String, String, String] = HFactory.createSliceQuery(keyspace,
          StringSerializer.get, StringSerializer.get, StringSerializer.get)

        query.setColumnFamily(columnFamilyId)
        query.setKey(rowId)
        query.setColumnNames(columnNames: _*)

        val result = query.execute()

        result.get().getColumns.asScala.map(kv => (kv.getName -> kv.getValue)).toMap
      }

    def Put(columnFamilyId: String, rowId: String, kvPairs: (String, String)*): Mutator[String] => Unit =
      keyspace =>{
        //todo
      }
  }

  object blocking {

    def Put(columnFamilyId: String, rowId: String, kvPairs: (String, String)*)(implicit mutator: Mutator[String]) {
      nonblocking.Put(columnFamilyId, rowId, kvPairs: _*)(mutator)
    }

    def Get(columnFamilyId: String, rowId: String, columnNames: String*)(implicit keyspace: Keyspace): Map[String, String] = {
      nonblocking.Get(columnFamilyId, rowId, columnNames: _*)(keyspace)
    }
  }

}
