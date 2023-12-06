fun main() {

    fun findNumberOfSolutionsBetterThanCurrentRecord(raceTime: Int, recordDistance: Int): Int {
        return (1 until raceTime).map { it * (raceTime - it) }.count { it > recordDistance }
    }

    fun part1(input: List<String>): Int {
        val times = input[0].split("Time:")[1].split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toInt() }
        val distances =
            input[1].split("Distance:")[1].split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toInt() }

        return times.zip(distances)
            .map { findNumberOfSolutionsBetterThanCurrentRecord(it.first, it.second) }
            .reduce { a, b -> a * b } // multiply all of the values together
    }

    fun part2(input: List<String>): Int {
        //TODO
        return 0
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)

    check(part2(testInput) == 0)

    val input = readInput("Day06")
    print("Part one answer is ") // ?
    part1(input).println()
    print("Part two answer is ") // ?
    part2(input).println()
}