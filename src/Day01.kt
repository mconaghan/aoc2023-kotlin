fun main() {

    fun getFirstAndLastDigits(line: String) : Int {
        val first = line.first{ it.isDigit()}
        val last = line.last{ it.isDigit()}
        return "$first$last".toInt()
    }

    fun convertRegexResult(value: String) : Int {
        return try {
            value.toInt()
        } catch (e: Exception) {
            when (value) {
                "one" -> 1
                "two" -> 2
                "three" -> 3
                "four" -> 4
                "five" -> 5
                "six" -> 6
                "seven" -> 7
                "eight" -> 8
                "nine" -> 9
                else -> 0
            }
        }
    }

    fun getFirstAndLastNumbers(line: String) : Int {
        val baseRegex = "(one|two|three|four|five|six|seven|eight|nine|\\d).*"
        val firstRegex = Regex(".*?$baseRegex")
        val lastRegex = Regex(".*$baseRegex")
        val first = convertRegexResult(firstRegex.matchEntire(line)?.groups?.get(1)?.value!!)
        val last = convertRegexResult(lastRegex.matchEntire(line)?.groups?.get(1)?.value!!)

        return "$first$last".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { getFirstAndLastDigits(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { getFirstAndLastNumbers(it) }
    }

    val testInputP1 = readInput("Day01P1_test")
    check(part1(testInputP1) == 142)

    val testInputP2 = readInput("Day01P2_test")
    check(part2(testInputP2) == 281)

    val input = readInput("Day01")
    print("Part one answer is ")
    part1(input).println()
    print("Part two answer is ")
    part2(input).println()
}