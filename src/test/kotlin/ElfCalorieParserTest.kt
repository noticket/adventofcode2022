import org.junit.jupiter.api.Test
import java.io.File

internal class ElfCalorieParserTest {

  @Test
  fun findCaloriesForElfWithMostCalories() {
    val parser = ElfCalorieParser(File(javaClass.getResource("/input.txt").file))
    println(parser.findCaloriesForElfWithMostCalories())
  }

  @Test
  fun findCaloriesForElfWithThreeMostCalories() {
    val parser = ElfCalorieParser(File(javaClass.getResource("/input.txt").file))
    println(parser.findCaloriesForElvesWithThreeMostCalories())
  }
}