import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class Day3Test {

    @Test
    fun calculatePrioritySum() {
        val day3 = Day3(File(javaClass.getResource("/Day3Problem1.txt").file))
        println("Sum: ${day3.calculatePrioritySum()}")
    }

    @Test
    fun calculateBadgePrioritySum() {
        val day3 = Day3(File(javaClass.getResource("/Day3Problem2.txt").file))
        println("Badge Sum: ${day3.calculateBadgePrioritySum()}")
    }
}