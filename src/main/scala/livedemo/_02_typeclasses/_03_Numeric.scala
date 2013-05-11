package livedemo._02_typeclasses

object _03_Numeric extends App{

  def multiply[A](vector1 : List[A], vector2 : List[A])
                 (implicit num: Numeric[A]): A = (
    for {
      e1 <- vector1
      e2 <- vector2
    } yield num.times(e1,e2)
    ).sum

  println ( multiply ( List(1L, 2L), List(3L, 5L) ))
  println( multiply ( List(1.2, 2.3), List(3.4, 5.3) ))

}