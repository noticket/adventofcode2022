import org.junit.jupiter.api.Test

import java.io.File

class RockPaperScissorsTest {

    @Test
    fun calculateScoresForSpecificPlays() {
        val parser = RockPaperScissors(File(javaClass.getResource("/RockPaperScissorsWinningStrategyInput.txt").file))
        println("Specific plays score: " + parser.calculateScoreForSpecificPlays())
    }

    @Test
    fun calculateScoresForDesiredOutcomes() {
        val parser = RockPaperScissors(File(javaClass.getResource("/RockPaperScissorsEncryptedGuide.txt").file))
        println("Desired outcome score: " + parser.calculateScoreForDesiredOutcomes())
    }
}