import org.junit.jupiter.api.Test

import java.io.File

class Day4Test {

    @Test
    fun countFullAndPartialOverlappingPairs() {
        val day4 = Day4(File(javaClass.getResource("/Day4Problem1.txt").file))
        println("Overlapping pairs: ${day4.countOverlappingPairs()}")
    }

    @Test
    fun countFullyOverlappingPairs() {
        val day4 = Day4(File(javaClass.getResource("/Day4Problem1.txt").file))
        println("Fully overlapping pairs: ${day4.countFullyOverlappingPairs()}")
    }
}