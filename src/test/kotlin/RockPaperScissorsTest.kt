import org.junit.jupiter.api.Test

import java.io.File

class RockPaperScissorsTest {

    @Test
    fun calculateScoresForSpecificPlays() {
        val parser = RockPaperScissors(File(javaClass.getResource("/Day2Problem1.txt").file))
        println("Specific plays score: " + parser.calculateScoreForSpecificPlays())
    }

    @Test
    fun calculateScoresForDesiredOutcomes() {
        val parser = RockPaperScissors(File(javaClass.getResource("/Day2Problem2.txt").file))
        println("Desired outcome score: " + parser.calculateScoreForDesiredOutcomes())
    }
}