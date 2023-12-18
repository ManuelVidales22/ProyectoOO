import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import taller4.Taller4._

import scala.collection.immutable.Seq

@RunWith(classOf[JUnitRunner])
class TestReconstruirCadenaIngenuo extends AnyFunSuite {


  test("TestReconstruirCadenaIngenuo") {
    val oraculo: Oraculo = (s: Seq[Char]) => s == Seq('a', 'x', 'b', 'a')
    val resultado = reconstruirCadenaIngenuo(3, oraculo)
    assert(resultado.isEmpty)
  }
  test("TestReconstruirCadenaIngenuo2") {
    val oraculo = generarOraculo(2)
    val resultado = reconstruirCadenaIngenuo(2, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaIngenuo3") {
    val oraculo = generarOraculo(3)
    val resultado = reconstruirCadenaIngenuo(3, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaIngenuo4") {
    val oraculo = generarOraculo(4)
    val resultado = reconstruirCadenaIngenuo(4, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }





}
