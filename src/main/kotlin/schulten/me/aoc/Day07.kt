package schulten.me.aoc

private fun evaluate(test: Long, current: Long, numbers: List<Long>, concatenation: Boolean): Boolean {
  if (numbers.isEmpty()) return test == current

  val next = numbers.first()
  val tail = numbers.drop(1)

  return evaluate(test, current + next, tail, concatenation) ||
    evaluate(test, current * next, tail, concatenation) ||
    (concatenation && evaluate(test, "$current$next".toLong(), tail, true))
}

fun main() {
  val calibrations = lines("/input/Day07.txt").map { calibration ->
    val values = calibration.split(' ')
    values.first().dropLast(1).toLong() to values.drop(1).map { it.toLong() }
  }

  // Part 1
  println(calibrations.filter { (test, numbers) -> evaluate(test, numbers.first(), numbers.drop(1), false) }.sumOf { it.first })

  // Part 2
  println(calibrations.filter { (test, numbers) -> evaluate(test, numbers.first(), numbers.drop(1), true) }.sumOf { it.first })
}