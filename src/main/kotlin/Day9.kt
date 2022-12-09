import java.awt.Point
import java.io.File
import java.lang.IllegalArgumentException
import kotlin.math.abs

class Day9 (private val numberOfKnots: Int, private val inputFile: File) {
    private val startPosition = Point(0, 0)

    private var knots: List<Point> = List(numberOfKnots) {
        _ -> Point(0, 0)
    }

    fun simulateMovements(): Int {
        val headPosition = knots[0]
        val allPositions: MutableSet<Point> = mutableSetOf(Point(startPosition))
        inputFile.useLines {
            it.map { line -> line.trim() }.let { lines ->
                for (move in lines) {
                    val (dir, magnitude) = move.split(' ')
                    val (x, y) = when (dir) {
                        "U" -> Pair(0, magnitude.toInt())
                        "D" -> Pair(0, -magnitude.toInt())
                        "R" -> Pair(magnitude.toInt(), 0)
                        "L" -> Pair(-magnitude.toInt(), 0)
                        else -> throw IllegalArgumentException("Invalid Input")
                    }
                    if (x != 0) {
                        repeat(abs(x)) {
                            val step = if (x > 0) 1 else -1
                            headPosition.translate(step, 0)
                            for (i in 1 until knots.size) {
                                ensureTailIsCloseEnough(knots[i - 1], knots[i], allPositions)
                            }
                        }
                    }
                    if (y != 0) {
                        repeat(abs(y)) {
                            val step = if (y > 0) 1 else -1
                            headPosition.translate(0, step)
                            for (i in 1 until knots.size) {
                                ensureTailIsCloseEnough(knots[i - 1], knots[i], allPositions)
                            }
                        }
                    }
                }
            }
        }
        return allPositions.count()
    }

    private fun ensureTailIsCloseEnough(head: Point, tail: Point, visitedPoints: MutableSet<Point>) {
        var horizontalDistance = head.x - tail.x
        var verticalDistance = head.y - tail.y
        var allPoints: Set<Point> = visitedPoints
        while (abs(horizontalDistance) + abs(verticalDistance) >= 2 &&
            !(abs(horizontalDistance) == 1 && abs(verticalDistance) == 1)) {
            val horizontalDirection = when {
                horizontalDistance > 0 -> 1
                horizontalDistance < 0 -> -1
                else -> 0
            }
            val verticalDirection = when {
                verticalDistance > 0 -> 1
                verticalDistance < 0 -> -1
                else -> 0
            }
            assert(verticalDirection != 0 || horizontalDirection != 0)
            tail.translate(horizontalDirection, verticalDirection)
            if (tail ===  knots.last()) {
                visitedPoints += Point(tail)
            }
            horizontalDistance = head.x - tail.x
            verticalDistance = head.y - tail.y
        }
    }
}