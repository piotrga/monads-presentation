package livedemo._01_implicits

import akka.util.Duration
import akka.util.duration._



object Implicits{
  def f1{/*...*/}
  def f2{/*...*/}
  def f3{/*...*/}

  def await(obj: => Any)(implicit timeout: Duration){/*...*/}

  implicit val timeout = 10 seconds

  await (f1)
  await (f2)
  await (f3)
  await (f1)

}