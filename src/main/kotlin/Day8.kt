import java.io.File
import kotlin.math.max

class Day8(val inputFile: File) {

    fun countVisibleTrees(): Int {
        val treeGrid = getTreeGrid()
        val height = treeGrid.size
        val width = treeGrid[0].size
        var visibleCount = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                val treeHeight = treeGrid[y][x]

                fun isVisibleFromRight(): Boolean {
                    for (index in x + 1 until width) {
                        if (treeGrid[y][index] >= treeHeight) {
                            return false
                        }
                    }
                    return true
                }
                fun isVisibleFromLeft(): Boolean {
                    for (index in x - 1 downTo 0) {
                        if (treeGrid[y][index] >= treeHeight) {
                            return false
                        }
                    }
                    return true
                }
                fun isVisibleFromBelow(): Boolean {
                    for (index in y + 1 until height) {
                        if (treeGrid[index][x] >= treeHeight) {
                            return false
                        }
                    }
                    return true
                }
                fun isVisibleFromAbove(): Boolean {
                    for (index in y - 1 downTo 0) {
                        if (treeGrid[index][x] >= treeHeight) {
                            return false
                        }
                    }
                    return true
                }

                if (x == 0 || y == 0 || x == width -1 || y == width - 1) {
                    visibleCount++
                } else if(isVisibleFromAbove() || isVisibleFromBelow() || isVisibleFromLeft() || isVisibleFromRight()) {
                    visibleCount++
                }
            }
        }
        return visibleCount
    }

    fun findMaxScenicScore(): Int {
        val treeGrid = getTreeGrid()
        val height = treeGrid.size
        val width = treeGrid[0].size
        var maxScore = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    continue
                }

                val treeHeight = treeGrid[y][x]

                fun fromRight(): Int {
                    for (index in x + 1 until width) {
                        if (treeGrid[y][index] >= treeHeight) {
                            return index - x
                        }
                    }
                    return width - x - 1
                }
                fun fromLeft(): Int {
                    for (index in x - 1 downTo 0) {
                        if (treeGrid[y][index] >= treeHeight) {
                            return x - index
                        }
                    }
                    return x
                }
                fun fromBelow(): Int {
                    for (index in y + 1 until height) {
                        if (treeGrid[index][x] >= treeHeight) {
                            return index - y
                        }
                    }
                    return height - y - 1
                }
                fun fromAbove(): Int {
                    for (index in y - 1 downTo 0) {
                        if (treeGrid[index][x] >= treeHeight) {
                            return y - index
                        }
                    }
                    return y
                }
                val above = fromAbove()
                val below = fromBelow()
                val right = fromRight()
                val left = fromLeft()
                val scenicScore = above * below * left * right
                maxScore = max(maxScore, scenicScore)
            }
        }
        return maxScore
    }

    private fun getTreeGrid(): List<List<Int>> {
        inputFile.useLines { input ->
            return input.map { line -> line.map { char -> char.digitToInt() } }.toList()
        }
    }
}