/**
  * Taller 3 - Programación Funcional
  * Autores: <Estudiantes>
  * Profesor: Carlos A Delgado
  */
package taller4
import common._
import org.scalameter.measure
import org.scalameter.withWarmer
import org.scalameter.Warmer
import scala.collection.parallel.CollectionConverters._
import org.scalameter.measure
import org.scalameter._
import scala.util.Random

object Taller4{
  // Definir el alfabeto
  val alfabeto = Seq('a', 'c', 'g', 't')
  // Definir el tipo Oraculo como una función
  type Oraculo = Seq[Char] => Boolean

  //metodo para generar oraculo aleatorio
  def generarOraculo(longitud: Int): Seq[Char] = {
    Seq.fill(longitud)(alfabeto(Random.nextInt(alfabeto.length)))
  }

  //metodo para reconstruir cadena ingenuo
  def reconstruirCadenaIngenuo(n: Int, o: Oraculo): Seq[Char] = {
    def auxiliar(secuencia: Seq[Char], n: Int): Seq[Char] = {
      if (n == 0) { // Si n es 0, devolvemos la secuencia
        secuencia
      } else {
        // Si n no es 0, añadimos cada letra del alfabeto a la secuencia y llamamos recursivamente a la función
        alfabeto.map(letra => auxiliar(secuencia :+ letra, n - 1)).find(o).getOrElse(Seq())
      }
    }

    auxiliar(Seq(), n) // Llamamos a la función auxiliar con una secuencia vacía y n
  }

  //metodo para reconstruir cadena ingenuo parallel preguntar por el umbral
  def reconstruirCadenaIngenuoParallel(umbral: Int)(n: Int, o: Oraculo): Seq[Char] = {
    def auxiliar(secuencia: Seq[Char], n: Int): Seq[Char] = {
      if (n == 0) {
        secuencia
      } else {
        if (n < umbral && o(secuencia)) {
          // Realizar llamadas de manera secuencial para tamaños pequeños
          reconstruirCadenaIngenuo(n, o)
        } else {
          // Utilizar par.map para realizar llamadas en paralelo con task
          val resultadosParalelos = alfabeto.par.map { letra =>
            task(auxiliar(secuencia :+ letra, n - 1)).join() // Utilizar task para crear una tarea
          }
          // Utilizar find sobre la secuencia resultante de forma no paralela
          resultadosParalelos.find(o).getOrElse(Seq())
        }
      }
    }

    auxiliar(Seq(), n)
  }




  //metodo para reconstruir cadena mejorado

  def ReconstruirCadenaMejorado(n: Int, o: Oraculo): Seq[Char] = {
    def auxiliar(secuencia: Seq[Char], k: Int): Seq[Char] = {
      if (k == n && o(secuencia)) { // Si la secuencia es de tamaño n y es aceptada por el oráculo, devolvemos la secuencia
        secuencia
      } else if (k < n) { // Si la secuencia es de tamaño menor que n, la dividimos en dos y llamamos recursivamente a la función
        alfabeto.map(letra => auxiliar(secuencia :+ letra, k + 1)).find(o).getOrElse(Seq())
      } else {
        Seq()
      }
    }

    auxiliar(Seq(), 0)
  }

  //metodo para reconstruir cadena mejorado parallel usando metodo parallel de common para paralelizar
  def ReconstruirCadenaMejoradoPar(umbral: Int)(n: Int, o: Oraculo): Seq[Char] = {
    def auxiliarParalelo(secuencia: Seq[Char], k: Int): Seq[Char] = {
      if (k == n && o(secuencia)) {
        secuencia
      } else if (k < n) {
        if (n < umbral) {
          // Realizar llamadas de manera secuencial para tamaños pequeños
          ReconstruirCadenaMejorado(n, o)
        } else {
          // Utilizar par.map para realizar llamadas en paralelo con task
          val resultadosParalelos = alfabeto.par.map { letra =>
            task(auxiliarParalelo(secuencia :+ letra, k + 1)).join() // Utilizar task para crear una tarea
          }
          // Utilizar find sobre la secuencia resultante de forma no paralela
          resultadosParalelos.find(o).getOrElse(Seq())
        }
      } else {
        Seq()
      }
    }

    auxiliarParalelo(Seq(), 0)
  }


  //metodo para reconstruir cadena turbo




  def pruebas(): Unit = {
    val tamanios = Seq(2, 4, 8, 16) // Diferentes tamaños de cadena para probar

    // Imprimir encabezado de la tabla
    println(f"| Tamaño | IngenuoPar (ms) |(ms)Oráculo |")
    for (tamano <- tamanios) {
      val oraculo = generarOraculo(tamano)

      // Medir tiempo de ejecución para reconstruirCadenaIngenuo
      val tiempoIngenuo = withWarmer(new Warmer.Default) measure {
        val resultadoIngenuo = reconstruirCadenaIngenuo(tamano, (s: Seq[Char]) => s == oraculo)
      }
      val res = reconstruirCadenaIngenuo(tamano, (s: Seq[Char]) => s == oraculo)


      // Imprimir resultados en formato de tabla
      println(f"| $tamano%6d | ${tiempoIngenuo.value}%12.4f |  |${res}%10s |")
    }
  }

  def compararAlgoritmos(funcionSecuencial: (Int, Oraculo) => Seq[Char], funcionParalela: (Int, Oraculo) => Seq[Char])
                        (n: Int, o: Oraculo): (Double, Double, Double) = {

    // Paralelo
    val tiempoParalelo = withWarmer(new Warmer.Default) measure {
      funcionParalela(n, o)
    }
    // Secuencial
    val tiempoSecuencial = withWarmer(new Warmer.Default) measure {
      funcionSecuencial(n, o)
    }


    // Calcular aceleración
    val aceleracion = tiempoSecuencial.value / tiempoParalelo.value
    // Devolver resultados
    (tiempoSecuencial.value, tiempoParalelo.value, aceleracion)
  }

  //comparar secuenciales vs paralelos
  def pruebasCompararAlgoritmos(): Unit = {
    val tamanios = Seq(4, 16, 32, 64) // Diferentes tamaños de cadena para probar
    //imprimir encabezado de la tabla
    println(f"| Tamaño | Mejorado (ms) | Mejorado Parallel (ms) | Aceleracion (ms) |Oráculo |")
    //usar metodo comparar algoritmos para comparar ingenuo vs parallel
    for (tamano <- tamanios) {
      val oraculo = generarOraculo(tamano)
      val (tiempoSecuencial, tiempoParalelo, aceleracion) = compararAlgoritmos(ReconstruirCadenaMejorado, ReconstruirCadenaMejoradoPar(4))(tamano, (s: Seq[Char]) => s == oraculo)
      println(f"| $tamano%6d | ${tiempoSecuencial}%12.4f | ${tiempoParalelo}%14.4f | ${aceleracion}%14.4f |  ${oraculo}%10s |")
    }
  }


  def main(args: Array[String]): Unit = {

    println("Pruebas comparar algoritmos")
    //pruebas()



  }



 }
