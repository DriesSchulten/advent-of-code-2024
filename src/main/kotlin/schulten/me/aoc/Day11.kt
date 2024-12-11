package schulten.me.aoc

fun main() {
  val input = text("/input/Day11.txt").numbers().map { it.toLong() }

  fun run(times: Int): Long {
    fun calc(value: Long): List<Long> = when {
      value == 0L -> listOf(1L)
      value.toString().length % 2 == 0 -> {
        val text = value.toString()
        val length = text.length / 2
        listOf(text.take(length).toLong(), text.drop(length).toLong())
      }
      else -> listOf(value * 2024)
    }

    val grouped = input.groupBy { it }.mapValues { it.value.size.toLong() }

    return (0..<times).fold(grouped) { acc, _ ->
      val temp = acc.map { (value, count) ->
        calc(value).groupBy { it }.mapValues { it.value.size }.map { entry ->
          entry.key to entry.value * count
        }.toMap()
      }

      temp.flatMap { it.entries }.groupBy({ it.key }, { it.value }).mapValues { it.value.sum() }
    }.values.sum()
  }

  // Part 1
  println(run(25))

  // Part 2
  println(run(75))
}