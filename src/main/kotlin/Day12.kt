import java.awt.Point
import java.io.File
import java.lang.IllegalArgumentException
import java.lang.Integer.max
import kotlin.math.abs
import kotlin.math.min

class Day12(private val inputFile: File) {

    class Node(val position: Point, val height: Char) {
        var visited: Boolean = false
        var weight: Int = Int.MAX_VALUE

        fun canTraverse(other: Node): Boolean {
            return (other.height - height) <= 1
        }

        fun copyOf(): Node {
            return Node(Point(position), height)
        }

        override fun hashCode(): Int {
            return position.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (other is Node) {
                return position == other.position
            }
            return false
        }
    }

    class GraphCalculator(val matrix: Array<Array<Node>>, private val startingPoint: Point, private val endingPoint: Point) {
        val width = matrix.first().size
        val height = matrix.size
        private val startingNode = matrix[startingPoint.y][startingPoint.x]
        private val endingNode = matrix[endingPoint.y][endingPoint.x]

        fun shortestPathLength(): Int {
            assignWeights()
            return endingNode.weight
        }

        private fun assignWeights() {
            val toVisit: MutableList<Node> = mutableListOf(startingNode)
            while (!toVisit.isEmpty()) {
                val node = toVisit.removeAt(0)
                val neighborsToVisit = assignWeightsToNeighborsOf(node).filterNot { toVisit.contains(it) }
                neighborsToVisit.forEach { it.weight = min(node.weight + 1, it.weight) }
                toVisit.addAll(neighborsToVisit)
                node.visited = true
            }
        }

        private fun assignWeightsToNeighborsOf(current: Node): List<Node> {
            val neighbors = mutableListOf<Node>()
            if (current.position.x > 0) {
                neighbors.add(matrix[current.position.y][current.position.x - 1])
            }
            if (current.position.y > 0) {
                neighbors.add(matrix[current.position.y - 1][current.position.x])
            }
            if (current.position.x < width - 1) {
                neighbors.add(matrix[current.position.y][current.position.x + 1])
            }
            if (current.position.y < height - 1) {
                neighbors.add(matrix[current.position.y + 1][current.position.x])
            }
            return neighbors.filter { node -> !node.visited && current.canTraverse(node) }
        }


    }

    fun calculateAllShortestPaths(): Int {
        val inputLines = inputFile.readLines().map { it.trim() }.toList()
        val starts: MutableList<Node> = mutableListOf()
        var end: Node? = null
        val matrix = inputLines.mapIndexed { y, line ->
            line.mapIndexed { x, char ->
                when (if (char == 'S') 'a' else char) {
                    'a' -> {
                        val node = Node(Point(x, y), 'a')
                        starts.add(node)
                        node
                    }
                    'E' -> {
                        val node = Node(Point(x, y), 'z')
                        end = node
                        node
                    }

                    else -> Node(Point(x, y), char)
                }
            }.toTypedArray()
        }.toTypedArray()

        if (end == null) {
            throw IllegalArgumentException("invalid input")
        }

        fun copyMatrix(startingPoint: Point): Array<Array<Node>> {
            val copy = matrix.map { row -> row.map { node -> node.copyOf() }.toTypedArray() }.toTypedArray()
            copy[startingPoint.y][startingPoint.x].weight = 0
            return copy
        }

        val lengths = starts.map { start ->
            GraphCalculator(copyMatrix(start.position), start.position, end!!.position).shortestPathLength()
        }
        return lengths.min()
    }

    fun calculateShortestPathFromStart(): Int {
        val inputLines = inputFile.readLines().map { it.trim() }.toList()
        var start: Node? = null
        var end: Node? = null
        val matrix = inputLines.mapIndexed { y, line ->
            line.mapIndexed { x, char ->
                when (char) {
                    'S' -> {
                        val node = Node(Point(x, y), 'a').apply { weight = 0 }
                        start = node
                        node
                    }

                    'E' -> {
                        val node = Node(Point(x, y), 'z')
                        end = node
                        node
                    }

                    else -> Node(Point(x, y), char)
                }
            }.toTypedArray()
        }.toTypedArray()
        if (start == null || end == null) {
            throw IllegalArgumentException("invalid input")
        }
        val calculator = GraphCalculator(matrix, start!!.position, end!!.position)
        return calculator.shortestPathLength()
    }
}