package livedemo.typeclasses

import java.io.{FileOutputStream, File, OutputStream}

object _03_Serialization{

  implicit object AddressJsonSerializer extends Serializable[Address]{
    def write(obj: Address, out: OutputStream) {
      out.write( """{
          'houseNumber' : '%s',
          'street' : '%s',
          'postCode' : '%s'
          }""".format(obj.houseNumber, obj.postCode, obj.street).getBytes("UTF-8"))
    }
  }

  case class Address(houseNumber: String, street: String, postCode: String)
  writeToFile(Address("11A", "South Colonnade", "E14 4BY"), "/tmp/demo.txt")

  def writeToFile[T](obj: T, filename: String)(implicit serializer: Serializable[T]) {
    val out = new FileOutputStream(filename)
    try{
      serializer.write(obj, out)
    } finally {
      out.close()
    }
  }

  trait Serializable[T] {
    def write(obj: T, out: OutputStream)
  }


}