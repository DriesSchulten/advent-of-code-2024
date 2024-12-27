package schulten.me.aoc

data class Region(val name: Char, val area: Int, val perimeter: Int)

fun Array<CharArray>.findRegion(start: Point, seen: MutableSet<Point>): Region {
  val target = this[start]
  val queue = mutableListOf(start)

  var area = 0
  var perimeter = 0

  while (queue.isNotEmpty()) {
    val plant = queue.removeFirst()

    if (plant in this && this[plant] == target && plant !in seen) {
      seen += plant
      area++
      val neighbors = plant.neighbours(this)
      queue.addAll(neighbors)
      perimeter += neighbors.count { it !in this || this[it] != target }
    }
  }

  return Region(target, area, perimeter)
}

fun main() {
  val map = lines("/input/Day12.txt").toGrid()

  val seen = mutableSetOf<Point>()
  val plants = map.indices.flatMap { x -> map[x].indices.map { y -> Point(x, y) } }.mapNotNull { plant ->
    if (plant !in seen) {
      map.findRegion(plant, seen)
    } else {
      null
    }
  }

  println(plants.sumOf { region -> region.area * region.perimeter })
}

