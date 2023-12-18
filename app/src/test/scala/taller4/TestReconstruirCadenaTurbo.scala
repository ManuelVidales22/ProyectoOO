package taller4
import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import scala.collection.immutable.Seq
import taller4.Taller4._
class TestReconstruirCadenaTurbo extends AnyFunSuite {
  //crear test para hacer preguntas al oraculo
  test("TestReconstruirCadenaTurbo") {
    val oraculo = generarOraculo(2)
    val resultado = ReconstruirCadenaTurbo(2, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaTurbo2") {
    val oraculo = generarOraculo(4)
    val resultado = ReconstruirCadenaTurbo(4, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }



}
