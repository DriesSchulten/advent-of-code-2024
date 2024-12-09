package schulten.me.aoc

fun main() {
  val tables = lines("/input/Day08.txt")

  val antennas = tables.flatMapIndexed { y, line ->
    line.mapIndexed { x, char ->
      Point(x, y) to char
    }
  }.filter { it.second != '.' }.groupBy(keySelector = { it.second }, valueTransform = { it.first })

  fun determinePositions(resonantHarmonics: Boolean = false): Int {
    val (maxX, maxY) = tables[0].length to tables.size
    fun inRange(point: Point): Boolean = point.x in 0..<maxX && point.y in 0..<maxY

    return antennas.flatMap { entry ->
      val list = entry.value
      val all = (list * list).filter { it.first != it.second }

      all.flatMap { (a1, a2) ->
        val dX = a1.x - a2.x
        val dY = a1.y - a2.y

        val sequence = if (!resonantHarmonics) {
          sequenceOf(1)
        } else {
          generateSequence(0) { it + 1 }
        }

        sequence.map { mul -> Point(a1.x + dX * mul, a1.y + dY * mul) }.takeWhile { inRange(it) }.toList() +
          sequence.map { mul -> Point(a2.x - dX * mul, a2.y - dY * mul) }.takeWhile { inRange(it) }.toList()
      }
    }.distinct().size
  }

  // Part 1
  println(determinePositions())

  // Part 2
  println(determinePositions(resonantHarmonics = true))
}