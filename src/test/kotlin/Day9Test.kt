import org.junit.jupiter.api.Test
import java.io.File

class Day9Test {

    @Test
    fun simulateMovements() {
        val testObj: Day9 = Day9(2, File(javaClass.getResource("/Day9Problem1.txt").file))
        println("Tail Movements: ${testObj.simulateMovements()}")
    }

    @Test
    fun simulate10Knots() {
        val testObj: Day9 = Day9(10, File(javaClass.getResource("/Day9Problem1.txt").file))
        println("Tail Movements: ${testObj.simulateMovements()}")
    }
}