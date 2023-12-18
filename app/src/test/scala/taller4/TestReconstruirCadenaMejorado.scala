import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import taller4.Taller4._

import scala.collection.immutable.Seq
@RunWith(classOf[JUnitRunner])
class TestReconstruirCadenaMejorado extends AnyFunSuite{
  //crear test para hacer preguntas al oraculo

  test("TestReconstruirCadenaMejorado") {
    val oraculo = generarOraculo(2)
    val resultado = ReconstruirCadenaMejorado(2, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }

  test("TestReconstruirCadenaMejorado2") {
    val oraculo = generarOraculo(4)
    val resultado = ReconstruirCadenaMejorado(4, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }

  test("TestReconstruirCadenaMejorado3") {
    val oraculo = generarOraculo(8)
    val resultado = ReconstruirCadenaMejorado(8, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }






}
