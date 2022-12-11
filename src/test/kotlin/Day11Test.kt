import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class Day11Test {

    @Test
    fun simulateMonkeys() {
        val testObj: Day11 = Day11(File(javaClass.getResource("/Day11Problem1.txt").file))
        println("Monkey business: ${testObj.simulateMonkeys(20, true)}")
    }

    @Test
    fun simulateMonkeys10000() {
        val testObj: Day11 = Day11(File(javaClass.getResource("/Day11Problem1.txt").file))
        println("Monkey business: ${testObj.simulateMonkeys(10000, false)}")
    }
}