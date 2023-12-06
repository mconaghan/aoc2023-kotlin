fun main() {

    fun findNumberOfSolutionsBetterThanCurrentRecord(raceTime: Long, recordDistance: Long): Int {
        return (1 until raceTime).map { it * (raceTime - it) }.count { it > recordDistance }
    }

    fun part1(input: List<String>): Int {
        val times = input[0].split("Time:")[1].split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toLong() }
        val distances =
            input[1].split("Distance:")[1].split("\\s+".toRegex()).filter { it.isNotBlank() }.map { it.toLong() }

        return times.zip(distances)
            .map { findNumberOfSolutionsBetterThanCurrentRecord(it.first, it.second) }
            .reduce { a, b -> a * b } // multiply all of the values together
    }

    fun part2(input: List<String>): Int {
        val time = input[0].split("Time:")[1].split("\\s+".toRegex()).joinToString("").toLong()
        val distance = input[1].split("Distance:")[1].split("\\s+".toRegex()).joinToString("").toLong()

        return findNumberOfSolutionsBetterThanCurrentRecord(time, distance)
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)

    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    print("Part one answer is ") // ?
    part1(input).println()
    print("Part two answer is ") // ?
    part2(input).println()
}