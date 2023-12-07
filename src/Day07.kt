fun main() {

    fun part1(input: List<String>): Long {
        val hands = input.map {
            val tokens = it.split(" ")
            Hand(tokens[0], tokens[1].toInt())
        }
        val sorted = hands.sortedWith(
            compareBy(
                { it.getRank() },
                { it.getCard1() },
                { it.getCard2() },
                { it.getCard3() },
                { it.getCard4() },
                { it.getCard5() })
        )

        println(sorted)

        var total = 0L
        sorted.forEachIndexed { index, hand ->
            total += ((index + 1) * hand.bid)
            val rank = hand.getRank()
            println("Total is now $total (${index + 1} x ${hand.bid}) - $hand $rank")
        }
        return total
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440L)

    check(part2(testInput) == 0L)

    val input = readInput("Day07")
    print("Part one answer is ") // NOT 255125074 | 255027346 is too low |
    part1(input).println()
    print("Part two answer is ") // ?
    part2(input).println()
}