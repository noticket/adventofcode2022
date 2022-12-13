import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.File

class Day12Test {

    @Test
    fun calculateShortestPath() {
        val testObj: Day12 = Day12(File(javaClass.getResource("/Day12Problem1.txt").file))
        println("Shortest path: ${testObj.calculateShortestPathFromStart()}")
    }

    @Test
    fun calculateAllShortestPaths() {
        val testObj: Day12 = Day12(File(javaClass.getResource("/Day12Problem1.txt").file))
        println("All Shortest path: ${testObj.calculateAllShortestPaths()}")
    }
}