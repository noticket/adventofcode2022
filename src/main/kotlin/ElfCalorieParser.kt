import java.io.File

class ElfCalorieParser(val inputFile: File) {
  fun findCaloriesForElfWithMostCalories(): Int {
    var maxValue = Int.MIN_VALUE
    inputFile.useLines {
      it.plus("").fold(0) { sum, line ->
        when (line.trim()) {
          "" -> {
            maxValue = maxOf(sum, maxValue)
            0
          }
          else -> {
            sum + line.toInt()
          }
        }
      }
    }
    return maxValue
  }
}
