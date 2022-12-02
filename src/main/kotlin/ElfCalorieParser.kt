import java.io.File
import java.util.PriorityQueue

class ElfCalorieParser(val inputFile: File) {
  fun findCaloriesForElfWithMostCalories(): Int {
    return generateMaxQueue().poll()
  }

  fun findCaloriesForElvesWithThreeMostCalories(): Int {
    val queue = generateMaxQueue()
    return queue.poll() + queue.poll() + queue.poll()
  }
  private fun generateMaxQueue(): PriorityQueue<Int> {
    val elvesMaxHeap: PriorityQueue<Int> = PriorityQueue(reverseOrder())
    inputFile.useLines { lines ->
      var currentValue = 0
      val terminator = -1
      for (value in lines.plus(" ").map { it.trim() }.map { if (it.isEmpty()) terminator else it.toInt() }) {
        if (value == terminator) {
          elvesMaxHeap.offer(currentValue)
          currentValue = 0
        } else {
          currentValue += value
        }
      }
    }
    return elvesMaxHeap
  }
}
