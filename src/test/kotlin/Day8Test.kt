import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class Day8Test {

    @Test
    fun countVisibleTrees() {
        val testObj: Day8 = Day8(File(javaClass.getResource("/Day8Problem1.txt").file))
        println("Num visible trees: ${testObj.countVisibleTrees()}")
    }

    @Test
    fun maxScenicScore() {
        val testObj: Day8 = Day8(File(javaClass.getResource("/Day8Problem1.txt").file))
        println("Max scenic score: ${testObj.findMaxScenicScore()}")
    }
}