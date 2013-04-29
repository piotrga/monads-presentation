package livedemo.typeclasses

import java.io.{FileOutputStream, File, OutputStream}

case class Address(houseNumber: String, street: String, postCode: String)

object JsonProtocol{
  implicit object AddressJsonSerializer extends Serializable[Address]{
    def write(obj: Address, out: OutputStream) {
      out.write( """{
          'houseNumber' : '%s',
          'street' : '%s',
          'postCode' : '%s'
          }""".format(obj.houseNumber, obj.postCode, obj.street).getBytes("UTF-8"))
    }
  }
}

trait Serializable[T] {
  def write(obj: T, out: OutputStream)
}

object _03_Serialization{

  def writeToFile[T](filename: String, obj: T)(implicit serializer: Serializable[T]) {
    val out = new FileOutputStream(filename)
    try{
      serializer.write(obj, out)
    } finally {
      out.close()
    }
  }

  import JsonProtocol._
  writeToFile("/tmp/demo.txt", Address("11A", "South Colonnade", "E14 4BY"))


}