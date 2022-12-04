import java.io.File
import java.lang.IllegalArgumentException

class RockPaperScissors(val inputFile: File) {
    val shapeValues = mapOf(
        "X" to 1,
        "Y" to 2,
        "Z" to 3
    )

    val ties = mapOf(
        "A" to "X",
        "B" to "Y",
        "C" to "Z"
    )

    val wins = mapOf(
        "A" to "Y",
        "B" to "Z",
        "C" to "X"
    )

    val losses = mapOf(
        "A" to "Z",
        "B" to "X",
        "C" to "Y"
    )

    val desiredOutcomeMap = mapOf(
        "X" to losses,
        "Y" to ties,
        "Z" to wins
    )

    fun calculateScoreForDesiredOutcomes(): Int {
        return inputFile.useLines {
            it.fold(0) { sum, line ->
                val trimmed = line.trim()
                if (trimmed.isEmpty()) return sum
                val (opponent, outcome) = trimmed.split(" ")
                val mine = desiredOutcomeMap.getValue(outcome).getValue(opponent)
                sum + calculateValue(opponent, mine)
            }
        }
    }

    fun calculateScoreForSpecificPlays(): Int {
        return inputFile.useLines {
            it.fold(0) { sum: Int, line ->
                val trimmed = line.trim()
                if (trimmed.isEmpty()) return sum
                val plays = trimmed.split(" ")
                sum + calculateValue(plays[0], plays[1])
            }
        }
    }
    fun calculateValue(opponent: String, mine: String): Int {
        return shapeValues.getValue(mine) + (if (wins[opponent] == mine) 6 else if (ties[opponent] == mine) 3 else 0)
    }
}