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
import java.util.concurrent._
/*Autores:
Alejandro Marin Hoyos 2259353-3743
Yessica Fernanda Villa Nuñez 2266301-3743
Manuel Antonio Vidales Duran  2155481-3743

 */
object Taller4 {
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


  //Metodo para ReconstruirCadenaTurbo
  def ReconstruirCadenaTurbo(n: Int, o: Oraculo): Seq[Char] = {
    def buscarCadena(secuencia: Seq[Seq[Char]]): Seq[Char] =
      secuencia.collectFirst { // Devolver la primera secuencia que cumpla con el oráculo
        case actual if o(actual) => actual
      }.getOrElse(Seq.empty)

    def Auxiliar(k: Int, secuencia: Seq[Seq[Char]]): Seq[Seq[Char]] =
      if (k >= n) secuencia // Si k es mayor o igual que n, devolvemos la secuencia
      else Auxiliar(k + 1, secuencia.flatMap(c => alfabeto.map(c :+ _))) // Si k es menor que n, añadimos cada letra del alfabeto a la secuencia y llamamos recursivamente a la función

    buscarCadena(Auxiliar(1, alfabeto.map(Seq(_))))
  }

  //metodo para reconstruir cadena turbo parallismo usando common parallel
  def ReconstruirCadenaTurboPar(umbral: Int)(n: Int, o: Oraculo): Seq[Char] = {
    def buscarCadena(secuencia: Seq[Seq[Char]]): Seq[Char] =
      secuencia.collectFirst {
        case actual if o(actual) => actual
      }.getOrElse(Seq.empty)

    def Auxiliar(k: Int, secuencia: Seq[Seq[Char]]): Seq[Seq[Char]] =
      if (k >= n) secuencia
      else {
        val (tarea1, tarea2) = parallel(
          secuencia.flatMap(c => alfabeto.map(c :+ _)),
          secuencia.flatMap(c => alfabeto.map(c :+ _))
        )
        Auxiliar(k + 1, tarea1 ++ tarea2)
      }

    buscarCadena(Auxiliar(1, alfabeto.map(Seq(_))))
  }


  def pruebas(): Unit = {
    val tamanios = Seq(4, 16) // Diferentes tamaños de cadena para probar

    // Imprimir encabezado de la tabla
    println(f"| Tamaño | Turbo(ms) |(ms)Oráculo |")
    for (tamano <- tamanios) {
      val oraculo = generarOraculo(tamano)

      // Medir tiempo de ejecución para reconstruirCadenaIngenuo
      val tiempoIngenuo = withWarmer(new Warmer.Default) measure {
        val resultadoIngenuo = ReconstruirCadenaTurboPar(4)(tamano, (s: Seq[Char]) => s == oraculo)
      }
      val res = ReconstruirCadenaTurboPar(4)(tamano, (s: Seq[Char]) => s == oraculo)


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
    val tamanios = Seq(2, 4, 8, 10, 12) // Diferentes tamaños de cadena para probar
    //imprimir encabezado de la tabla
    println(f"| Tamaño | Turbo (ms) | Turbo Parallel (ms) | Aceleracion (ms) |Oráculo |")
    //usar metodo comparar algoritmos para comparar ingenuo vs parallel
    for (tamano <- tamanios) {
      val oraculo = generarOraculo(tamano)
      val (tiempoSecuencial, tiempoParalelo, aceleracion) = compararAlgoritmos(reconstruirCadenaIngenuo, reconstruirCadenaIngenuoParallel(4))(tamano, (s: Seq[Char]) => s == oraculo)
      println(f"| $tamano%6d | ${tiempoSecuencial}%12.4f | ${tiempoParalelo}%14.4f | ${aceleracion}%14.4f |  ${oraculo}%10s |")
    }
  }


  def main(args: Array[String]): Unit = {

    println("Pruebas comparar algoritmos")
    pruebasCompararAlgoritmos()


  }





 }
