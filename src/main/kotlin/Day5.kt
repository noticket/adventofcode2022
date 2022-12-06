import java.io.File
import java.lang.IllegalArgumentException

class Day5(val inputFile: File) {

    private val headerStart = Regex("""^\s\d+\s.*""")
    private val headerEnd = Regex(""".*\s(\d+)\s$""")

    data class MoveInstruction(val from: Int, val to: Int, val numBoxes: Int) {
        companion object {
            private val pattern = Regex(
                """.*move (?<count>\d+) from (?<from>\d+) to (?<to>\d+).*""", RegexOption.IGNORE_CASE
            )

            fun matches(line: String) = pattern.matches(line)
            fun parse(line: String): MoveInstruction? {
                val parsed = pattern.find(line)
                return parsed?.let {
                    val numBoxes = it.groups.get("count")?.value?.toInt()
                    val from = it.groups.get("from")?.value?.toInt()?.minus(1)
                    val to = it.groups.get("to")?.value?.toInt()?.minus(1)
                    return when {
                        numBoxes != null && from != null && to != null -> MoveInstruction(from, to, numBoxes)
                        else -> null
                    }
                }
            }
        }
    }

    data class Row(private val _rows: List<Char?>) {
        fun getBox(index: Int): Char? = _rows.getOrNull(index)

        companion object {

            private val pattern = Regex("""\[([A-Z])]""")

            fun matches(line: String) = pattern.find(line) != null

            fun parse(numStacks: Int, line: String): Row =
                pattern.findAll(line).associate {
                    (it.range.first) / 4 to it.groups[1]?.value
                }.let { matches ->
                    Row(List(numStacks) {
                        matches[it]?.get(0)
                    })
                }
        }
    }

    private class Stacks(var numStacks: Int, rows: List<Row>) {

        val stacks: Map<Int, ArrayDeque<Char?>>

        fun boxesOnTopOfStacks(): String = stacks.mapNotNull { it.value.firstOrNull() }.joinToString("")

        init {
            stacks = buildMap {
                for (index in 0 until numStacks) {
                    this[index] = ArrayDeque(rows.mapNotNull { row -> row.getBox(index) })
                }
            }
        }

        fun applyMoveWithOldCrane(move: MoveInstruction): Unit {
            repeat(move.numBoxes) {
                stacks[move.to]?.addFirst(stacks[move.from]?.removeFirst())
            }
        }

        fun applyMoveWithNewCrane(move: MoveInstruction): Unit {
            stacks[move.from]?.take(move.numBoxes)?.let {
                stacks[move.to]?.addAll(0, it)
            }
            repeat(move.numBoxes) {
                stacks[move.from]?.removeFirst()
            }
        }
    }

    private fun isHeader(line: String) = headerStart.matches(line) && headerEnd.matches(line)

    private fun rearrangeStacks(applyMove: (stacks: Stacks, move: MoveInstruction) -> Unit): String {
        val group = inputFile.useLines { lines ->
            lines.groupBy { line ->
                when {
                    isHeader(line) -> "header"
                    Row.matches(line) -> "row"
                    MoveInstruction.matches(line) -> "move"
                    else -> "empty"
                }
            }
        }

        val numStacks = group["header"]?.get(0)?.let {
            headerEnd.find(it)?.groups?.get(1)?.value?.toInt()
        } ?: throw IllegalArgumentException("Bad input")

        val stacks = group["row"]?.let { rowGroup ->
            Stacks(
                numStacks = numStacks,
                rows = rowGroup.map { rowString -> Row.parse(numStacks, rowString) }
            )
        } ?: throw IllegalArgumentException("Bad input")

        group["move"]?.map(MoveInstruction::parse)?.forEach {
            it?.let { instruction ->
                applyMove(stacks, instruction)
            }
        }
        return stacks.boxesOnTopOfStacks()
    }

    fun rearrangeCargoWithOldCrane(): String {
        return rearrangeStacks(Stacks::applyMoveWithOldCrane)
    }

    fun rearrangeCargoWithNewCrane(): String {
        return rearrangeStacks(Stacks::applyMoveWithNewCrane)
    }
}