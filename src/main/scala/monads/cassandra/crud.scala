package monads.cassandra

import me.prettyprint.hector.api.Keyspace
import me.prettyprint.hector.api.mutation.Mutator
import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.cassandra.serializers.StringSerializer
import monads.cassandra.Composition_After.ReaderMonad
import monads.cassandra.TypeClasses_After.CassandraObject

object crud{
  object nonblocking{
    implicit def mutationReaderToOperation[B](m: ReaderMonad[Mutator[String], B]): ReaderMonad[Keyspace, B] = new ReaderMonad[Keyspace, B] {
      def apply(keyspace: Keyspace): B = {
        val mutator = HFactory.createMutator(keyspace, StringSerializer.get())
        val res = m(mutator)
        mutator.execute()
        res
      }
    }
    implicit def mutationToOperation[B](m: Mutator[String] => B): ReaderMonad[Keyspace, B] = mutationReaderToOperation(m)

    def Save[T](o: T)(implicit cas : CassandraObject[T]): Mutator[String] => Unit =
      simpleoperations.nonblocking.Put(cas.columnFamily, cas.rowId(o), cas.marshall(o): _*)

    def Read[T](id: String)(implicit cas : CassandraObject[T]): Keyspace => Option[T] =
      k =>  None
  }

  object blocking{

    def Save[T](t: T)(implicit mutator: Mutator[String], cas: CassandraObject[T])  = nonblocking.Save(t)(cas)(mutator)
    def Read[T](id: String)(implicit keyspace: Keyspace, cas: CassandraObject[T]) = nonblocking.Read(id)(cas)(keyspace)

  }
}