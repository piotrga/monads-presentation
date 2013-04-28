package livedemo.implicits

import akka.util.Duration
import akka.util.duration._



object Implicits{
  def f1{/*...*/}
  def f2{/*...*/}
  def f3{/*...*/}

  def await(obj: => Any, timeout: Duration){/*...*/}

  await (f1, 10 seconds)
  await (f2, 10 seconds)
  await (f3, 10 seconds)
  await (f1, 10 seconds)

}