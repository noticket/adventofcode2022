import java.io.File
import java.lang.IllegalStateException

class Day13(private val inputFile: File) {

    fun sumCorrectlyOrderedIndices(): Int {
        val packetPairs = inputFile.readLines().chunked(3).map { it.take(2) }.mapIndexed { index, stringPair ->
            PacketPair(index + 1, PacketParser.parsePacket(stringPair[0]), PacketParser.parsePacket(stringPair[1]))
        }
        return packetPairs.sumOf { if (it.areInCorrectOrder()) it.index else 0 }
    }

    fun decoderKey(): Int {
        val divider2 = PacketParser.parsePacket("[[2]]")
        val divider6 = PacketParser.parsePacket("[[6]]")
        val packets = inputFile.readLines().filter { it.isNotBlank() }.map { PacketParser.parsePacket(it) }
            .plus(divider2)
            .plus(divider6)
            .sortedWith { left, right -> if (left.correctOrder(right)) -1 else 1 }
            .toList()
        return (packets.indexOf(divider2) + 1) * (packets.indexOf(divider6) + 1)
    }

    data class PacketEntry(val value: Int? = null, val list: List<PacketEntry>? = null) {
        init {
            assert(value == null || list == null)
            assert(value != null || list != null)
        }

        override fun toString(): String {
            return buildString {
                if (value != null) {
                    append(value)
                }
                if (list != null) {
                    append('[')
                    append(list.map { it.toString() }.joinToString(","))
                    append(']')
                }
            }
        }

        fun correctOrder(right: PacketEntry): Boolean {
            return correctOrderInternal(right) ?: throw IllegalStateException("Unable to find an answer")
        }

        private fun correctOrderInternal(right: PacketEntry): Boolean? {
            if (value != null) {
                if (right.value != null) {
                    if (value == right.value) {
                        return null
                    }
                    return value < right.value
                }
                val valueAsList = PacketEntry(list = listOf(this))
                return valueAsList.correctOrderInternal(right)
            }
            if (right.value != null) {
                val valueAsList = PacketEntry(list = listOf(right))
                return correctOrderInternal(valueAsList)
            }
            if (list != null && right.list != null) {
                val leftIterator = list.iterator()
                val rightIterator = right.list.iterator()
                while (leftIterator.hasNext()) {
                    if (!rightIterator.hasNext()) {
                        return false
                    }
                    val leftEntry = leftIterator.next()
                    val rightEntry = rightIterator.next()
                    val isCorrect = leftEntry.correctOrderInternal(rightEntry)
                    if (isCorrect != null) {
                        return isCorrect
                    }
                }
                if (rightIterator.hasNext()) {
                    return true
                }
                return null
            }
            throw IllegalStateException("Neither list or value are present")
        }
    }

    class PacketParser {
        companion object {

            fun parsePacket(packet: String): PacketEntry {
                return parseList(0, packet).second
            }

            fun parseNextValue(startingIndex: Int, packet: String): Pair<Int, PacketEntry> {
                val builder = StringBuilder()
                var index = startingIndex
                while (index < packet.length && packet[index].isDigit()) {
                    builder.append(packet[index])
                    index++
                }
                return Pair(index, PacketEntry(value = builder.toString().toInt()))
            }

            fun parseList(startingIndex: Int, packet: String): Pair<Int, PacketEntry> {
                assert(packet[startingIndex] == '[')
                var list = mutableListOf<PacketEntry>()
                var index = startingIndex + 1
                while (index < packet.length - 1 && packet[index] != ']') {
                    if (packet[index] == '[') {
                        val (endingIndex, nextList) = parseList(index, packet)
                        index = endingIndex + 1
                        list.add(nextList)
                        continue
                    }
                    if (packet[index] == ',') {
                        index++
                        continue
                    }
                    val (endingIndex, nextEntry) = parseNextValue(index, packet)
                    index = endingIndex
                    list.add(nextEntry)
                }
                return Pair(index, PacketEntry(list = list))
            }
        }
    }

    data class PacketPair(val index: Int, val left: PacketEntry, val right: PacketEntry) {
        fun areInCorrectOrder(): Boolean {
            return left.correctOrder(right)
        }
    }
}