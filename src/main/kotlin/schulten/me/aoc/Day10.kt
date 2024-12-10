package schulten.me.aoc

fun main() {
  val map = lines("/input/Day10.txt")

  val trailheads = buildList {
    map.indices.forEach { y ->
      map[y].indices.forEach { x ->
        if (map[y][x] == '0') {
          add(Point(x, y))
        }
      }
    }
  }

  fun inRange(point: Point): Boolean = point.x >= 0 && point.x < map.size && point.y >= 0 && point.y < map[0].length

  fun walk(current: Point, path: List<Point>): List<List<Point>> {
    val value = map[current.y][current.x]

    if (value == '9') {
      return listOf(path + current)
    }

    val neighbours = listOf(
      current + Direction.NORTH,
      current + Direction.EAST,
      current + Direction.SOUTH,
      current + Direction.WEST,
    ).filter { inRange(it) && map[it.y][it.x] == (value.digitToInt() + 1).digitToChar() }

    return neighbours.flatMap { walk(it, path + current) }
  }

  // Part 1
  println(trailheads.sumOf { walk(it, emptyList()).map { it.last() }.distinct().size })

  // Part 2
  println(trailheads.sumOf { walk(it, emptyList()).size })
}