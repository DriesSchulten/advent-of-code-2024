package schulten.me.aoc

val whitespaceRegex = "\\s+".toRegex()

fun text(file: String): String = resource(file).readText()

fun lines(file: String): List<String> = resource(file).readLines()

fun String.numbers(): List<Int> = this.split(whitespaceRegex).map(String::toInt)

private fun resource(file: String) = object {}.javaClass.getResourceAsStream(file)!!.bufferedReader(Charsets.UTF_8)

enum class Direction(val dx: Int, val dy: Int) {
  NORTH(0, -1),
  NORTHEAST(1, -1),
  EAST(1, 0),
  SOUTHEAST(1, 1),
  SOUTH(0, 1),
  SOUTHWEST(-1, 1),
  WEST(-1, 0),
  NORTHWEST(-1, -1);
}

infix fun Int.mod(mod: Int): Int {
  return if (this >= mod || this < 0) {
     val remainder = this % mod
     if (remainder < 0) remainder + mod else remainder
  } else {
    this
  }
}

data class Point(val x: Int, val y: Int) {
  operator fun plus(direction: Direction) = Point(x + direction.dx, y + direction.dy)
  operator fun plus(other: Point) = Point(x + other.x, y + other.y)
  operator fun times(multiplier: Int) = Point(x * multiplier, y * multiplier)
  infix fun mod(other: Point) = Point(x mod other.x, y mod other.y)
}

operator fun <T1, T2> Iterable<T1>.times(other: Iterable<T2>) = this.flatMap { a -> other.map { b -> a to b } }

fun List<String>.toGrid(): Triple<Array<CharArray>, Int, Int> {
  val maxX = first().length
  val maxY = size

  val array = (0..<maxX).map { CharArray(maxY) }.toTypedArray()

  withIndex().forEach { (y, row) ->
    row.withIndex().forEach { (x, c) ->
      array[x][y] = c
    }
  }

  return Triple(array, maxX, maxY)
}