package schulten.me.aoc

import kotlin.math.pow

private val numRegex = "(\\d+)".toRegex()

data class Computer(var registerA: Long, var registerB: Long, var registerC: Long) {

  private var instructionPointer = 0

  private fun comboValue(comboOperand: Int): Long =
    when (comboOperand) {
      in 0..3 -> comboOperand.toLong()
      4 -> registerA
      5 -> registerB
      6 -> registerC
      else -> error("Unknown combo operand: $comboOperand")
    }

  fun executeProgram(program: List<Int>): List<Long> =
    buildList {
      while (instructionPointer < program.size) {
        val instruction = Instruction.fromOpcode(program[instructionPointer])
        val operand = program[instructionPointer + 1]

        when (instruction) {
          Instruction.ADV, Instruction.BDV, Instruction.CDV -> {
            val numerator = registerA
            val denominator = 2.0.pow(comboValue(operand).toDouble())

            val result = (numerator / denominator).toLong()
            when (instruction) {
              Instruction.ADV -> registerA = result
              Instruction.BDV -> registerB = result
              Instruction.CDV -> registerC = result
              else -> error("Impossible")
            }
          }

          Instruction.BXL -> registerB = registerB xor operand.toLong()
          Instruction.BST -> registerB = comboValue(operand) % 8
          Instruction.JNZ -> if (registerA != 0L) {
            instructionPointer = operand - 2
          }

          Instruction.BXC -> registerB = registerB xor registerC
          Instruction.OUT -> add(comboValue(operand) % 8)
        }

        instructionPointer += 2
      }
    }

  companion object {
    fun fromString(input: List<String>): Computer {
      val (a, b, c) = input.take(3).map { numRegex.find(it)!!.groupValues[1].toLong() }
      return Computer(a, b, c)
    }
  }
}

enum class Instruction(val opcode: Int) {
  ADV(0),
  BXL(1),
  BST(2),
  JNZ(3),
  BXC(4),
  OUT(5),
  BDV(6),
  CDV(7);

  companion object {
    fun fromOpcode(opcode: Int): Instruction = entries.first { it.opcode == opcode }
  }
}

fun main() {
  val input = lines("/input/Day17.txt")

  val computer = Computer.fromString(input)
  val program = input.last().split(" ").last().split(",").map { it.toInt() }

  println(computer.copy().executeProgram(program).joinToString(separator = ","))

  println(program.reversed().map { it.toLong() }.fold(listOf(0L)) { candidates, instruction ->
    candidates.flatMap { candidate ->
      val shifted = candidate shl 3
      (shifted..shifted + 8).mapNotNull { attempt ->
        computer.copy().run {
          registerA = attempt
          attempt.takeIf { executeProgram(program).first() == instruction }
        }
      }
    }
  }.first())
}