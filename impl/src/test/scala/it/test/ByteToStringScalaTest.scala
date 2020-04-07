package it.test

object ByteToStringScalaTest extends App {

  val bytes: Array[Byte] = Array(83, 101, 114, 118, 101, 114, 32, 105, 115, 32, 114, 117, 110, 110, 105, 110, 103, 46, 46, 46, 46)

  val str = (bytes.map(_.toChar)).mkString
  println(str)

}
