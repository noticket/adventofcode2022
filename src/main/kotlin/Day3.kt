import java.io.File

class Day3(val inputFile: File) {

    fun calculateBadgePrioritySum(): Int = generateBadgePrioritiesPerGroup().sum()

    fun calculatePrioritySum(): Int = generateForEachLine { calculateDuplicateItemPriority(it) }.sum()

    private fun charPriority(char: Char) = (char - 'A').xor(32).let { it + if (it > 31) -5 else 1 }

    private fun lineToPriorityList(line: String) = line.map(::charPriority)

    private fun calculateDuplicateItemPriority(line: String): Int =
        lineToPriorityList(line).let {
            it.subList(0, line.length / 2).toSet()
                .intersect(it.subList(line.length / 2, line.length).toSet())
                .reduce { acc, value -> acc + value }
        }

    private fun generateBadgePrioritiesPerGroup(): Sequence<Int> {
        return generateForEachLine { lineToPriorityList(it) }.let {
            it.map { it.toSet() }.chunked(3) { elfGroup ->
                elfGroup.reduce { left, right -> left.intersect(right) }
                    .single()
            }
        }
    }

    private fun <T> generateForEachLine(func: (String) -> T) = sequence {
        inputFile.useLines {
            it.forEach { line -> yield(func(line.trim())) }
        }
    }
}