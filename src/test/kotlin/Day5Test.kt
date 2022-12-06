import org.junit.jupiter.api.Test

import java.io.File

class Day5Test {

    @Test
    fun rearrangeCargoWithOldCrane() {
        val day5 = Day5(File(javaClass.getResource("/Day5Problem1.txt").file))
        println("Boxes on top after using old crane: ${day5.rearrangeCargoWithOldCrane()}")
    }

    @Test
    fun rearrangeCargoWithNewCrane() {
        val day5 = Day5(File(javaClass.getResource("/Day5Problem1.txt").file))
        println("Boxes on top after using new crane: ${day5.rearrangeCargoWithNewCrane()}")
    }
}