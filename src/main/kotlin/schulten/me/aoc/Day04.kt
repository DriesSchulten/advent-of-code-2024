package schulten.me.aoc


fun main() {
  val table = lines("/input/Day04.txt");

  // Part 1
  println(table.indices.sumOf { y ->
    table[y].indices.sumOf { x ->
      Direction.entries.count { dir ->
        "XMAS".withIndex().all { (idx, c) ->
          table.getOrNull(y + idx * dir.dy)?.getOrNull(x + idx * dir.dx) == c
        }
      }
    }
  })

  // Part 2
  println(table.indices.sumOf { y ->
    table[y].indices.count { x ->
      if (table[y][x] == 'A') {
        val north = table.getOrNull(y - 1)
        val south = table.getOrNull(y + 1)

        val northWest = north?.getOrNull(x - 1);
        val northEast = north?.getOrNull(x + 1);
        val southWest = south?.getOrNull(x - 1);
        val southEast = south?.getOrNull(x + 1);

        (north != null && south != null) &&
          (northWest == 'M' && southEast == 'S' || northWest == 'S' && southEast == 'M') &&
          (northEast == 'M' && southWest == 'S' || northEast == 'S' && southWest == 'M')
      } else {
        false
      }
    }
  })
}