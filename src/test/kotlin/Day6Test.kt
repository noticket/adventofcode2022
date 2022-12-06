import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class Day6Test {

    @Test
    fun findStartOfStreamIndex() {
        val day6 = Day6(File(javaClass.getResource("/Day6Problem1.txt").file))
        println("Start of stream: ${day6.findStartOfStreamIndex()}")
    }

    @Test
    fun findStartOfMessageIndex() {
        val day6 = Day6(File(javaClass.getResource("/Day6Problem1.txt").file))
        println("Start of message: ${day6.findStartOfMessageIndex()}")
    }
}