package schulten.me.aoc

fun main() {
  val table = lines("/input/Day06.txt")

  fun findStart(): Pair<Int, Int> {
    val y = table.indexOfFirst { it.contains('^') }
    val x = table[y].indexOfFirst { it == '^' }
    return x to y
  }

  fun nextDirection(direction: Direction) = when (direction) {
    Direction.NORTH -> Direction.EAST
    Direction.EAST -> Direction.SOUTH
    Direction.SOUTH -> Direction.WEST
    Direction.WEST -> Direction.NORTH
    else -> throw IllegalArgumentException("Invalid direction $direction")
  }

  fun part1(): Int {
    val current = findStart()
    fun walk(current: Pair<Int, Int>, direction: Direction): Set<Pair<Int, Int>> {
      val seen = setOf(current)
      val (x, y) = direction.dx + current.first to direction.dy + current.second

      return seen + if (table.getOrNull(y) == null || table[y].getOrNull(x) == null) {
        emptySet()
      } else if (table[y][x] == '#') {
        val nextDirection = nextDirection(direction)
        walk(nextDirection.dx + current.first to nextDirection.dy + current.second, nextDirection)
      } else {
        walk(x to y, direction)
      }
    }

    val seen = walk(current, Direction.NORTH)
    return seen.size
  }

  println(part1());
}