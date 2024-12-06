package schulten.me.aoc

fun main() {
  val input = lines("/input/Day05.txt")

  val rawRules = input.takeWhile { it != "" }.map { line ->
    val (a, b) = line.split('|')
    a.toInt() to b.toInt()
  }
  val rules = rawRules.groupBy({ it.first }, { it.second })
  val reverseRules = rawRules.groupBy({ it.second }, { it.first })

  val updates = input.dropWhile { it != "" }.drop(1).map { line ->
    line.split(',').map { it.toInt() }
  }

  fun isOrdered(update: List<Int>): Boolean = update.mapIndexed { idx, page ->
    val rule = rules[page]
    val reverseRule = reverseRules[page]

    (rule == null || update.drop(idx + 1).all { rule.contains(it) }) //&&
      //(reverseRule == null || update.take(idx).all { reverseRule.contains(it) })
  }.all { it }

  // Part 1
  println(updates.filter { isOrdered(it) }.sumOf { it[it.size / 2] })

  fun fixOrder(update: List<Int>): List<Int> = update.mapIndexed { idx, page ->
    page
  }

  // Part 2
  println(updates.filterNot { isOrdered(it) }.map { fixOrder(it) }.sumOf { it[it.size / 2] })
}