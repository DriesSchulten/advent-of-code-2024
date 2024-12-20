package schulten.me.aoc

data class Robot(val position: Point, val velocity: Point) {
  companion object {
    fun fromString(string: String): Robot {
      val (position, velocity) = string.split(" ").flatMap {
        it.split("=")[1].split(",").map { num -> num.toInt() }.chunked(2).map { (x, y) -> Point(x, y) }
      }
      return Robot(position, velocity)
    }
  }

  fun move(max: Point) = copy(position = (position + velocity) mod max)
}

fun advanceSecond(robots: List<Robot>, max: Point): List<Robot> {
  return robots.map { robot -> robot.move(max) }
}

fun printMap(robots: List<Robot>, max: Point) {
    (0..<max.y).forEach { y ->
      (0..<max.x).forEach { x ->
      val pos = Point(x, y)
      if (robots.any { it.position == pos }) {
        print('X')
      } else {
        print('.')
      }
    }
    println()
  }
}

fun main() {
  val robots = lines("/input/Day14.txt").map { Robot.fromString(it) }
  val max = Point(101, 103)

  fun part1() {
    val result = (0..<100).fold(robots) { state, _ -> advanceSecond(state, max) }

    val middle = Point(max.x / 2, max.y / 2)
    println(result.groupingBy { robot ->
      when {
        robot.position.x < middle.x && robot.position.y < middle.y -> 0
        robot.position.x < middle.x && robot.position.y > middle.y -> 1
        robot.position.x > middle.x && robot.position.y < middle.y -> 2
        robot.position.x > middle.x && robot.position.y > middle.y -> 3
        else -> -1
      }
    }.eachCount().filterKeys { it != -1 }.values.reduce(Int::times))
  }

  part1()

  fun part2() {
    var current = robots
    var seconds = 1
    while (true) {
      current = current.map { robot -> robot.move(max) }

      val positions = current.map { it.position }
      if (positions.any { pos -> (-3..3).map { pos.plus(Point(it, 0)) }.count { positions.contains(it) } == 7 }) {
        printMap(current, max)
        println(seconds)
        println()
      }

      seconds++
    }
  }

  part2()
}