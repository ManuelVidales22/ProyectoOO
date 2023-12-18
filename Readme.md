# Proyecto Oraculo  - Fundamentos de programación funcional y concurrente
## Autores

- Alejandro Marin Hoyos (ID: 2259353-3743)
- Yessica Fernanda Villa Nuñez (ID: 2266301-3743)
- Manuel Antonio Vidales Duran (ID: 2155481-3743)

## Introducción
Este proyecto aborda el desafío de la reconstrucción de secuencias de ADN, también conocidas como el Problema de la Reconstrucción de Cadenas (PRC). Los investigadores en Biología buscan identificar un patrón de nucleótidos, conocido como el genoma humano, que codifique la información necesaria para construir las proteínas en el cuerpo humano. La secuenciación del ADN implica la construcción de un patrón a partir de subpatrones presentes en la cadena de ADN. Dado que las secuencias de ADN son extremadamente largas (aproximadamente 3 billones de caracteres en el genoma humano), se requieren técnicas computacionales sofisticadas. Este proyecto se centra en la pregunta fundamental "¿es una cadena dada una subcadena de otra?", suponiendo un oráculo de laboratorio perfecto. El objetivo es abordar este problema mediante experimentos que utilizan un oráculo para determinar si una subsecuencia dada es parte de la cadena buscada.

## Implementación de Algoritmos y Comparación de Rendimiento
1.2. ReconstruirCadenaIngenuo:
Esta función reconstruye una cadena de ADN utilizando un enfoque ingenuo, generando todas las posibles secuencias y buscando aquella que cumple con la condición del oráculo.

## 2. Acelerando los Datos con Paralelismo de Tareas y de Datos
2.1. ReconstruirCadenaIngenuoParallel:
Esta función realiza la reconstrucción de una cadena de manera paralela para tamaños mayores o iguales al umbral especificado y de manera secuencial para tamaños menores al umbral.

## Tabla Comparativa
3.1. Tabla de Comparación del Método Ingenuo (Versión Secuencial y Paralela)
| Tamaño | Ingenuo (ms) | IngenuoPar (ms) | Aceleración (ms) |
|--------|--------------|-----------------|-------------------|
| 2      | 0.0360       | 1.6329          | 0.020             |
| 4      | 0.1753       | 1.8780          | 0.0933            |
| 8      | 14.0568      | 47.1289         | 0.2983            |
| 10     | 306.2730     | 611.5581        | 0.5008            |
| 12     | 4488.5878    | 10050.7203      | 0.4466            |



