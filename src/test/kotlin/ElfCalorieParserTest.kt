import java.io.File
import java.util.ResourceBundle
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

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