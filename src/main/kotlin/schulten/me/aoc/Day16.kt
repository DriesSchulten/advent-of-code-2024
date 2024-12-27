package schulten.me.aoc

import java.util.*
import kotlin.math.abs

fun Direction.rotationCost(other: Direction): Long {
  val diff = abs((Direction.entries.indexOf(this) - Direction.entries.indexOf(other)) / 2)
  return if (diff == 3) 1000L else diff * 1000L
}

fun pathCost(maze: Array<CharArray>, start: Point, end: Point): Long {
  val queue = PriorityQueue<Triple<Point, Direction, Long>>(compareBy { it.third }).apply { add(Triple(start, Direction.EAST, 0L)) }
  val seen: MutableMap<Pair<Point, Direction>, Long> = mutableMapOf((start to Direction.EAST) to 0L)

  while (queue.isNotEmpty()) {
    val (current, currentDirection, minValue) = queue.poll()

    if (current == end) {
      return minValue
    }

    val neighbours = neighbours(current).filter { maze[it.first] != '#' }

    neighbours.forEach { (delta, direction) ->
      val rotationCost = direction.rotationCost(currentDirection)
      val currentMin = seen.getOrDefault(delta to direction, Long.MAX_VALUE)
      val newDistance = minValue + 1 + rotationCost
      if (newDistance < currentMin) {
        seen[delta to direction] = newDistance
        queue.offer(Triple(delta, direction, newDistance))
      }
    }
  }

  return Long.MAX_VALUE
}

fun main() {
  val input = lines("/input/Day16.txt")
  val maze = input.takeWhile { it != "" }.toGrid()

  val start = maze.findPosition('S')!!
  val end = maze.findPosition('E')!!

  // Part 1
  println(pathCost(maze, start, end))
}