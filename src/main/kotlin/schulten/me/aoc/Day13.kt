package schulten.me.aoc

val deltaRegex = "\\+(\\d+)".toRegex()
val coordinateRegex = "=(\\d+)".toRegex()

data class Position(val x: Long, val y: Long) {
  operator fun plus(other: Position) = Position(x + other.x, y + other.y)
  operator fun times(multiplier: Long) = Position(x * multiplier, y * multiplier)
}

data class Button(val label: Char, val position: Position)

fun main() {
  val input = lines("/input/Day13.txt")
  val games = input.chunked(4).map { group ->
    val buttons = group.take(2).map { buttonLine ->
      val label = buttonLine.split(':')[0].last()
      val (deltaX, deltaY) = deltaRegex.findAll(buttonLine).toList()

      Button(label, Position(deltaX.groupValues[1].toLong(), deltaY.groupValues[1].toLong()))
    }

    val (prizeX, prizeY) = coordinateRegex.findAll(group[2]).map { it.groupValues[1].toLong() }.take(2).toList()
    val prize = Position(prizeX, prizeY)

    prize to buttons
  }

  fun solve(buttons: List<Button>, prize: Position, offset: Long = 0L): Long? {
    val (buttonA, buttonB) = buttons

    val ax = buttonA.position.x
    val bx = buttonB.position.x

    val px = prize.x + offset

    val ay = buttonA.position.y
    val by = buttonB.position.y

    val py = prize.y + offset

    val b = ((px * ay) - (py * ax)) / (bx * ay - by * ax)
    val a = (px - b * bx) / ax

    return if (buttonA.position * a + buttonB.position * b == Position(px, py)) {
      a * 3L + b
    } else {
      null
    }
  }

  println(games.mapNotNull { game -> solve(game.second, game.first) }.sum())
  println(games.mapNotNull { game -> solve(game.second, game.first, 10000000000000L) }.sum())
}
