package schulten.me.aoc

fun main() {
  val (map, maxX, maxY) = lines("/input/Day12.txt").toGrid()

  fun area(points: List<Point>): Int = points.size

  fun Point.inGrid(): Boolean = x in 0..<maxX && y in 0..<maxY

  fun perimeter(plant: Char, points: List<Point>): Int {
    fun perimeter(point: Point): Int =
      setOf(
        point + Direction.NORTH,
        point + Direction.EAST,
        point + Direction.SOUTH,
        point + Direction.WEST,
      ).filter { (it.inGrid() && map[it.x][it.y] != plant) || !it.inGrid() }.size

    val res = points.sumOf { perimeter(it) }
    println(res)
    return res
  }

  val plants =
    map.indices.flatMap { x -> map[x].indices.map { y -> map[x][y] to Point(x, y) } }.groupBy(keySelector = { it.first }, valueTransform = { it.second })
  println(plants.map { (plant, points) -> area(points) * perimeter(plant, points) }.sum())
}

