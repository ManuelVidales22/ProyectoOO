import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import scala.collection.immutable.Seq
import taller4.Taller4._
@RunWith(classOf[JUnitRunner])
class TestReconstruirCadenaMejoradoParalelo extends AnyFunSuite {
  //crear test para hacer preguntas al oraculo
  test("TestReconstruirCadenaMejoradoParallel") {
    val oraculo = generarOraculo(2)
    val resultado = ReconstruirCadenaMejoradoPar(4)(2, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaMejoradoParallel2") {
    val oraculo = generarOraculo(4)
    val resultado = ReconstruirCadenaMejoradoPar(4)(4, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }
  test("TestReconstruirCadenaMejoradoParallel3") {
    val oraculo = generarOraculo(8)
    val resultado = ReconstruirCadenaMejoradoPar(4)(8, (s: Seq[Char]) => s == oraculo)
    assert(resultado == oraculo)
  }



}
