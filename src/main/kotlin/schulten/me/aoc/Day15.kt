package schulten.me.aoc

fun Array<CharArray>.attemptMoveBox(boxPosition: Point, direction: Direction): Boolean {
  val next = boxPosition + direction

  return when (this[next]) {
    '.' -> this.swap(boxPosition, next).let { true }
    'O' -> {
      val candidates = generateSequence(next) { point -> point + direction }
      val emptySpace = candidates.takeWhile { this[it] != '#' }.firstOrNull { this[it] == '.' }
      if (emptySpace != null) {
        this.swap(boxPosition, emptySpace).let { true }
      } else {
        false
      }
    }

    else -> false
  }
}

fun calculateGPS(warehouse: Array<CharArray>): Long = warehouse.indices.flatMap { x ->
  warehouse[x].indices.mapNotNull { y ->
    if (warehouse[x][y] == 'O') {
      100L * y + x
    } else {
      null
    }
  }
}.sum()


fun main() {
  val input = lines("/input/Day15.txt")

  val warehouse = input.takeWhile { it != "" }.toGrid()
  val moves = input.takeLastWhile { it != "" }.joinToString(separator = "").toCharArray()

  val robot = warehouse.findPosition('@')!!

  moves.fold(robot) { currentPosition, move ->
    val direction = when (move) {
      '<' -> Direction.WEST
      '>' -> Direction.EAST
      '^' -> Direction.NORTH
      else -> Direction.SOUTH
    }

    val nextPosition = currentPosition + direction
    val value = warehouse[nextPosition]

    when (value) {
      '.' -> warehouse.swap(currentPosition, nextPosition).let { nextPosition }
      'O' -> if (warehouse.attemptMoveBox(nextPosition, direction)) warehouse.swap(currentPosition, nextPosition).let { nextPosition } else currentPosition
      else -> currentPosition
    }
  }

  println(calculateGPS(warehouse))
}