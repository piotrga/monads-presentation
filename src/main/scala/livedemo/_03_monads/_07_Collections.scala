package livedemo._03_monads


object _07_Collections extends App{

  List(1,2,3,4).map(a => a*a) // List(1, 4, 9, 16)
  List(1,2,3).flatMap(a => List.fill(a)(a*a) ) // List(1, 4, 4, 9, 9, 9)

  // Implicit conversion: Option -> Iterable

  def readBalance(id : String) : Option[Double] = id match {
    case "1" => Some(100)
    case "2" => Some(200)
    case _ => None
  }

  List("1","2","3") flatMap(id => readBalance(id) ) sum; // 300

}
