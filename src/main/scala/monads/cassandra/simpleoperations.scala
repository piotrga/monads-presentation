package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import me.prettyprint.hector.api.query.SliceQuery
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.cassandra.serializers.StringSerializer
import scala.collection.JavaConverters._
import me.prettyprint.hector.api.mutation.Mutator
import monads.cassandra.Composition_After.ReaderMonad

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

    implicit def mutationReaderToOperation[B](m: ReaderMonad[Mutator[String], B]): ReaderMonad[Keyspace, B] = new ReaderMonad[Keyspace, B] {
      def apply(keyspace: Keyspace): B = {
        val mutator = HFactory.createMutator(keyspace, StringSerializer.get())
        val res = m(mutator)
        mutator.execute()
        res
      }
    }
    implicit def mutationToOperation[B](m: Mutator[String] => B): ReaderMonad[Keyspace, B] = mutationReaderToOperation(m)

  }

  object blocking {

    def Get(columnFamilyId: String, rowId: String, columnNames: String*)
           (implicit keyspace: Keyspace): Map[String, String] =
      nonblocking.Get(columnFamilyId, rowId, columnNames: _*)(keyspace)


    def Put(columnFamilyId: String, rowId: String, kvPairs: (String, String)*)
           (implicit keyspace: Keyspace) {
      nonblocking.mutationToOperation(nonblocking.Put(columnFamilyId, rowId, kvPairs: _*))(keyspace)
    }

  }

}
