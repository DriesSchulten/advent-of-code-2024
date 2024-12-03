package schulten.me.aoc

private val mulRegex = "mul\\((\\d+),(\\d+)\\)".toRegex()
private val instRegex = "${mulRegex.pattern}|do\\(\\)|don't\\(\\)".toRegex()

fun main() {
  val input = text("/input/Day03.txt")

  // Part 1
  println(mulRegex.findAll(input).sumOf { matchResult -> matchResult.groupValues[1].toInt() * matchResult.groupValues[2].toInt() })

  // Part 2
  println(instRegex.findAll(input).fold(true to 0L) { curr, matchResult ->
    when {
      matchResult.groupValues[0] == "don't()" -> false to curr.second
      matchResult.groupValues[0] == "do()" -> true to curr.second
      curr.first -> true to curr.second + (matchResult.groupValues[1].toInt() * matchResult.groupValues[2].toInt())
      else -> curr
    }
  }.second)
}