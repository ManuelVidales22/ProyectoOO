package taller4
import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import scala.collection.immutable.Seq
import taller4.Taller4._
@RunWith(classOf[JUnitRunner])
class TestReconstruirCadenaTurboPar extends AnyFunSuite {
  //crear test para hacer preguntas al oraculo
  test("TestReconstruirCadenaTurboPar") {
    val oraculo = generarOraculo(2)
    val resultado = ReconstruirCadenaTurboPar(4)(2, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaTurboPar2") {
    val oraculo = generarOraculo(4)
    val resultado = ReconstruirCadenaTurboPar(4)(4, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }

}
