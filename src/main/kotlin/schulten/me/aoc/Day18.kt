package schulten.me.aoc

fun walk(end: Point, map: List<Point>): Int? {
  val queue = mutableListOf(Point(0, 0) to 0)
  val seen = mutableSetOf<Point>()

  while (queue.isNotEmpty()) {
    val (place, cost) = queue.removeFirst()

    if (place == end) {
      return cost
    } else if (seen.add(place)) {
      neighbours(place).map { it.first }.filter { it.inRange(end) }.filterNot { it in map }.forEach { queue.add(it to cost + 1) }
    }
  }

  return null
}

fun main() {
  val map = lines("/input/Day18.txt").take(1024).map { line ->
    val (x, y) = line.numbers()
    Point(x, y)
  }

  println(walk(Point(70, 70), map))
}