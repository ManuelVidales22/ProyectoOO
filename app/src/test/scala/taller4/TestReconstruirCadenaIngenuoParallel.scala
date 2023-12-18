import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import scala.collection.immutable.Seq
import taller4.Taller4._
@RunWith(classOf[JUnitRunner])
class TestReconstruirCadenaIngenuoParallel extends AnyFunSuite{
  //crear test para hacer preguntas al oraculo
  test("TestReconstruirCadenaIngenuoParallel") {
    val oraculo = generarOraculo(2)
    val resultado = reconstruirCadenaIngenuoParallel(4)(2, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaIngenuoParallel2") {
    val oraculo = generarOraculo(4)
    val resultado = reconstruirCadenaIngenuoParallel(4)(4, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaIngenuoParallel3") {
    val oraculo = generarOraculo(8)
    val resultado = reconstruirCadenaIngenuoParallel(4)(8, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaIngenuoParallel4") {
    val oraculo = generarOraculo(10)
    val resultado = reconstruirCadenaIngenuoParallel(4)(10, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }




}
