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
  NORTHWEST(-1, -1),
}