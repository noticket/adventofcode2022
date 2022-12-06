import java.io.File
import java.lang.IllegalArgumentException

class Day6(val inputFile: File) {

    fun findStartOfMessageIndex(): Int {
        val fullText = inputFile.readText()
        val offsets = ArrayList<String>(14)
        offsets.add(fullText)
        for (i in 1 until 14) {
            offsets.add(fullText.drop(i))
        }
        for (i in 0 until fullText.length - 14) {
            if (offsets.map{ it[i] }.toSet().size == 14) {
                return i + 14
            }
        }
        throw IllegalArgumentException("invalid input")
    }

    fun findStartOfStreamIndex(): Int {
        val fullText = inputFile.readText()
        val skip1 = fullText.drop(1)
        val skip2 = fullText.drop(2)
        val skip3 = fullText.drop(3)
        for (i in 0 until fullText.length - 4) {
            if (setOf(fullText[i], skip1[i], skip2[i], skip3[i]).size == 4) {
                return i + 4
            }
        }
        throw IllegalArgumentException("invalid input")
    }
}