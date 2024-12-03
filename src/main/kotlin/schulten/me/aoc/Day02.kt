package schulten.me.aoc

import kotlin.math.abs

fun main() {
  val reports = lines("/input/Day02.txt").map { it.numbers() }

  // Part 1
  println(reports.count { safe(it) })

  // Part 2
  println(reports.count { safe(it, allowDrop = true) })
}

private fun safe(report: List<Int>, allowDrop: Boolean = false): Boolean {
  return if (!allowDrop) {
    val differences = report.windowed(2).map { it.first() - it.last() }
    val all = differences.all { it < 0 } || differences.all { it > 0 }
    all && differences.map { abs(it) }.none { it >= 4 }
  } else {
    report.indices.any { safe(report.filterIndexed { idx, _ -> idx != it}) }
  }
}