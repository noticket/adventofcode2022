import java.io.File
import kotlin.math.abs

class Day10(private val inputFile: File) {

    fun getDrawing(): Sequence<String> {
        return yieldAfterCycles(::getLitOrDarkPixelChar).chunked(40).map {
            it.joinToString("")
        }
    }

    fun sumProblemOne(): Int {
        return sumSignalStrengths(setOf(20, 60, 100, 140, 180, 220))
    }

    private fun sumSignalStrengths(targetStrengths: Set<Int>): Int {
        return yieldAfterCycles(::getPairForSignalStrengthCalculations).fold(0) { acc, pair ->
            if (pair.first in targetStrengths) acc + pair.second else acc
        }
    }

    private fun <T> yieldAfterCycles(evaluator: (Int, Int) -> T?): Sequence<T> = sequence {
        inputFile.useLines { input ->
            var cycleCount = 0
            var x = 1

            fun incrementAndMaybeYield(): T? {
                cycleCount++
                return evaluator(cycleCount, x)
            }

            for (line in input.map { it.trim() }) {
                if (line == "noop") {
                    incrementAndMaybeYield()?.let { yield(it) }
                } else {
                    val (instruction, argument) = line.split(' ')

                    if (instruction == "addx") {
                        incrementAndMaybeYield()?.let { yield(it) }
                        incrementAndMaybeYield()?.let { yield(it) }
                        x += argument.toInt()
                    }
                }
            }
        }
    }

    private fun getLitOrDarkPixelChar(cycleCount: Int, registerX: Int): Char {
        return if (abs(registerX - ((cycleCount-1) % 40)) <= 1) '#' else '.'
    }

    private fun getPairForSignalStrengthCalculations(cycleCount: Int, registerX: Int): Pair<Int, Int>? {
        return when {
            cycleCount == 20 -> Pair(20, registerX * 20)
            (cycleCount - 20) % 40 == 0 -> Pair(cycleCount, cycleCount * registerX)
            else -> null
        }
    }
}