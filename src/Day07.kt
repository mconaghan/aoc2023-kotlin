fun main() {

    fun part1(input: List<String>): Long {
        val hands = input.map {
            val tokens = it.split(" ")
            Hand(tokens[0], tokens[1].toLong())
        }
        val sorted = hands.sortedWith(
            compareBy(
                { it.getRankPartOne() },
                { it.getCard1(true) },
                { it.getCard2(true) },
                { it.getCard3(true) },
                { it.getCard4(true) },
                { it.getCard5(true) })
        )

        var total = 0L
        sorted.forEachIndexed { index, hand ->
            total += ((index + 1) * hand.bid)
        }
        return total
    }

    fun part2(input: List<String>): Long {
        val hands = input.map {
            val tokens = it.split(" ")
            Hand(tokens[0], tokens[1].toLong())
        }

        val sorted = hands.sortedWith(
            compareBy(
                { it.getRankPartTwo() },
                { it.getCard1(false) },
                { it.getCard2(false) },
                { it.getCard3(false) },
                { it.getCard4(false) },
                { it.getCard5(false) })
        )

        var total = 0L
        sorted.forEachIndexed { index, hand ->
            total += ((index + 1) * hand.bid)
        }
        return total
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440L)
    check(part2(testInput) == 5905L)

    val input = readInput("Day07")
    print("Part one answer is ") // ?
    part1(input).println()
    print("Part two answer is ")
    part2(input).println()
}