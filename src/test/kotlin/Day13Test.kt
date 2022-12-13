import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class Day13Test {

    @Test
    fun sumCorrectlyOrderedIndices() {
        val testObj: Day13 = Day13(File(javaClass.getResource("/Day13Problem1.txt").file))
        println("Sum of correct indices: ${testObj.sumCorrectlyOrderedIndices()}")
    }

    @Test
    fun decoderKey() {
        val testObj: Day13 = Day13(File(javaClass.getResource("/Day13Problem1.txt").file))
        println("Decoder key: ${testObj.decoderKey()}")
    }
}