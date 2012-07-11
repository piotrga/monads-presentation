package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import me.prettyprint.hector.api.mutation.Mutator
import monads.cassandra.TypeClasses_After.CassandraObject
import monads.cassandra.Composition_After.fun2Reader

object crud{
  object nonblocking{

    def Save[T](o: T)(implicit cas : CassandraObject[T]): Keyspace => Unit =
      simpleoperations.nonblocking.Put(cas.columnFamily, cas.rowId(o), cas.marshall(o): _*)

    def Read[T](id: String)(implicit cas : CassandraObject[T]): Keyspace => Option[T] =
      simpleoperations.nonblocking.Get(cas.columnFamily, id, cas.columns: _*) map cas.unmarshall
  }

  object blocking{
    import simpleoperations.nonblocking.mutationToOperation

//    def Save[T](t: T)(implicit keyspace: Keyspace, cas: CassandraObject[T])  = mutationToOperation(nonblocking.Save(t)(cas))(keyspace)
    def Save[T](t: T)(implicit keyspace: Keyspace, cas: CassandraObject[T])  = nonblocking.Save(t)(cas)(keyspace)
    def Read[T](id: String)(implicit keyspace: Keyspace, cas: CassandraObject[T]) : Option[T] = nonblocking.Read(id)(cas)(keyspace)

  }
}