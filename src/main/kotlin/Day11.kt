import java.io.File

class Day11(private val inputFile: File) {

    class Monkey(
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val test: (Long) -> Int,
        val divisor: Long
    ) {
        private var itemsInspected: Int = 0

        fun inspectItem(item: Long): Long {
            itemsInspected++
            return operation(item)
        }

        fun itemsInspected(): Int = itemsInspected
    }

    fun simulateMonkeys(numRounds: Int, worryLessens: Boolean): Long {
        val monkeys = buildMonkeys().toList()

        val divideByThree = { input: Long -> input / 3 }

        // Make a number of which all divisors are a common factor... I don't remember how to do least multiple or
        // greatest factor so just multiply everything together to be the limit for worry, everything higher than this
        // number is useless anyway. We will use mod of this number to reset the worry without modifying which conditions
        // the number satisfies. 
        val multiple = monkeys.drop(1).fold(monkeys.first().divisor) { acc, monkey ->
            acc * monkey.divisor
        }
        val modDivisorMultiple = { input: Long -> input % multiple }

        val manageWorry = if (worryLessens) divideByThree else modDivisorMultiple

        repeat(numRounds) {
            for (monkey in monkeys) {
                //println("Monkey ${monkey.id}:")
                for (item in monkey.items) {
                    //println("  Monkey inspects an item with worry level of $item")
                    val afterInspection = monkey.inspectItem(item)
                    //println("    Worry level after inspection is $afterInspection")
                    val newWorryLevel = manageWorry(afterInspection)
                    //println("    Monkey gets bored with item. Worry level is divided by 3 to $newWorryLevel")
                    val targetMonkey = monkey.test(newWorryLevel)
                    //println("    Item with worry level $newWorryLevel is thrown to monkey $targetMonkey")
                    monkeys[targetMonkey].items.add(newWorryLevel)
                }
                monkey.items.clear()
            }
        }
        return monkeys.sortedByDescending { monkey -> monkey.itemsInspected() }.take(2).fold(1) { acc, monkey ->
            acc * monkey.itemsInspected()
        }
    }

    private fun buildMonkeys(): List<Monkey> {
        return inputFile.useLines { input ->
            input.map { it.trim() }.chunked(7).map { monkeyInputList ->
                val (_, id) = monkeyInputList[0].replace(":", "").split(" ")
                val itemList = monkeyInputList[1].split("s: ")[1].split(", ")
                val (operation, magnitude) = monkeyInputList[2].trim().split(" = old ")[1].split(' ')
                val (condition, argument) = monkeyInputList[3].trim().removePrefix("Test: ").split(" by ")
                val trueMonkeyId = monkeyInputList[4].trim().removePrefix("If true: throw to monkey ")
                val falseMonkeyId = monkeyInputList[5].trim().removePrefix("If false: throw to monkey ")
                Monkey(
                    items = itemList.map(String::toLong).toMutableList(),
                    operation = buildOperation(operation, magnitude),
                    test = buildTest(condition, argument, trueMonkeyId, falseMonkeyId),
                    divisor = argument.toLong()
                )
            }.toList()
        }
    }

    private fun buildOperation(operation: String, magnitude: String): (Long) -> Long {
        return when (operation) {
            "*" -> { input: Long -> input * if (magnitude == "old") input else magnitude.toLong() }
            "+" -> { input: Long -> input + if (magnitude == "old") input else magnitude.toLong() }
            else -> throw IllegalArgumentException("Invalid operation")
        }
    }

    private fun buildTest(
        condition: String,
        argument: String,
        trueMonkeyId: String,
        falseMonkeyId: String
    ): (Long) -> Int {
        return when (condition) {
            "divisible" -> { input ->
                when (input % argument.toLong()) {
                    0L -> trueMonkeyId.toInt()
                    else -> falseMonkeyId.toInt()
                }
            }

            else -> throw IllegalArgumentException("Invalid condition")
        }
    }
}
