package schulten.me.aoc

fun main() {
  var currentId = 0L

  val map = text("/input/Day09.txt").mapIndexed { idx, c -> c.digitToInt() to (idx % 2 == 0) }.flatMap { (num, isFile) ->
    val number = if (isFile) currentId else -1
    if (isFile) currentId++

    (0 until num).map { number }
  }

  // Part 1
  fun part1(diskMap: MutableList<Long>) {
    var rightIndex = diskMap.size - 1
    for (index in diskMap.indices) {
      if (diskMap[index] == -1L) {
        while (rightIndex > index && diskMap[rightIndex] == -1L) {
          rightIndex--
        }

        diskMap[index] = diskMap[rightIndex].also { diskMap[rightIndex] = -1L }
      }
    }

    println(diskMap.takeWhile { it != -1L }.mapIndexed { idx, id -> idx * id }.sum())
  }

  part1(map.toMutableList())
}







