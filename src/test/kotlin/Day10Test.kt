import org.junit.jupiter.api.Test
import java.io.File

class Day10Test {

    @Test
    fun sumProblemOne() {
        val testObj: Day10 = Day10(File(javaClass.getResource("/Day10Problem1.txt").file))
        println("Sum for problem one: ${testObj.sumProblemOne()}")
    }

    @Test
    fun printProblemTwo() {
        val testObj: Day10 = Day10(File(javaClass.getResource("/Day10Problem1.txt").file))
        testObj.getDrawing().forEach { println(it) }
    }
}