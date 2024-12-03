package schulten.me.aoc

import kotlin.math.abs

fun main() {
  val (a, b) = lines("/input/Day01.txt").map { line ->
    val parts = line.split(whitespaceRegex)
    parts[0].toInt() to parts[1].toInt()
  }.unzip()

  // Part 1
  println(a.sorted().zip(b.sorted()).sumOf { (a, b) -> abs(a - b) })

  // Part 2
  println(a.sumOf { cur -> cur.toLong() * b.count { it == cur } })
}