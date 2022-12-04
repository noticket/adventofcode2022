import java.io.File

class Day4(val inputFile: File) {

    private class ElfRange(rangeString: String) {
        private val range = rangeString.split('-').let { (start, end) -> start.toInt().rangeTo(end.toInt()) }

        fun isFullyOverlappingWith(other: ElfRange): Boolean {
            return range.intersect(other.range).let { it.size == range.count() || it.size == other.range.count() }
        }

        fun isOverlappingWith(other: ElfRange): Boolean {
            return range.union(other.range).size < (range.count() + other.range.count())
        }
    }

    private val lines = sequence {  inputFile.useLines { it.forEach { line -> yield(line) } } }

    private val elfRanges = lines.map { it.split(',').map { rangeString -> ElfRange(rangeString) } }

    fun countOverlappingPairs(): Int =
        elfRanges.count { (elf1, elf2) -> elf1.isOverlappingWith(elf2)}

    fun countFullyOverlappingPairs(): Int =
        elfRanges.count { (elf1, elf2) -> elf1.isFullyOverlappingWith(elf2) }
}