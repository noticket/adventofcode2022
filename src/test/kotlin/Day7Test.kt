import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class Day7Test {

    @Test
    fun sumAllSmallDirs() {
        val testObj: Day7 = Day7(File(javaClass.getResource("/Day7Problem1.txt").file))
        println("sumAllSmallDirs: ${testObj.sumAllSmallDirs()}")
    }

    @Test
    fun deleteEnoughSpace() {
        val testObj: Day7 = Day7(File(javaClass.getResource("/Day7Problem1.txt").file))
        println("deleteEnoughSpace: ${testObj.deleteEnoughSpace()}")
    }
}