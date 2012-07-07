package monads.cassandra


object Composition {
  import crud.nonblocking._
  // Update[Person]("joe-123", _.copy(name = "Smith"))

  // val p = Read[Person]("joe-123").get
  // Save( p copy (name = "Smith") )


}
