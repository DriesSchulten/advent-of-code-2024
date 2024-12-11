package schulten.me.aoc

fun main() {
  val input = text("/input/Day11.txt").numbers().map { it.toLong() }

  fun run(times: Int): Int {
    val result = (0..<times).fold(input) { acc, _ ->
      acc.flatMap { value ->
        if (value == 0L) {
          listOf(1)
        } else if (value.toString().length % 2 == 0) {
          val text = value.toString()
          val length = text.length / 2
          listOf(text.take(length).toLong(), text.drop(length).toLong())
        } else {
          listOf(value * 2024)
        }
      }
    }

    return result.size
  }

  println(run(25))
  println(run(75))
}