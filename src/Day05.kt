fun main() {

    fun lookupMap(mapName: String, id: Long, mapOfMaps: Map<String, Map<Long, Long>>): Long {
        // find the key which is immediately before the value
        var precedingKey = mapOfMaps[mapName]!!.keys.min()
        mapOfMaps[mapName]!!.keys.forEach {
            if (it in (precedingKey + 1)..<id) {
                precedingKey = it
            }
        }

        val precedingValue = mapOfMaps[mapName]!![precedingKey]!!
        val delta = id - precedingKey
        return precedingValue + delta
    }

    fun processData(
        dataToProcess: MutableMap<Long, LongArray>,
        mapOfMaps: MutableMap<String, Map<Long, Long>>,
        mapName: String
    ) {
        dataToProcess.toSortedMap().forEach {

            val sourceRangeStart = it.key
            val destRangeStart = it.value[0]
            val range = it.value[1]

            val map = mapOfMaps[mapName]!!.toMutableMap()
            map[sourceRangeStart] = destRangeStart
            map[sourceRangeStart + range] = destRangeStart + range
            map[sourceRangeStart + range + 1] = sourceRangeStart + range + 1

            val keysToDelete = map.keys.filter { it > sourceRangeStart && it < (sourceRangeStart + range) }
            keysToDelete.forEach { map.remove(it) }
            mapOfMaps[mapName] = map
        }
        dataToProcess.clear()
    }

    fun part1(input: List<String>): Long {

        val seeds = input[0].split("seeds:")[1].split("\\s+".toRegex()).filterNot { it.isBlank() }.map { it.toLong() }

        val mapOfMaps = mutableMapOf<String, Map<Long, Long>>()
        var mapName = ""
        val dataToProcess = mutableMapOf<Long, LongArray>()
        for (line in input.subList(1, input.size)) {
            val regexMatch = Regex("(.*) map:").matchEntire(line)
            if (regexMatch != null) {
                mapName = regexMatch.groups[1]!!.value
                mapOfMaps[mapName] = mutableMapOf(0L to 0L)
            } else if (line.isBlank()) {
                processData(dataToProcess, mapOfMaps, mapName)
            } else {
                // should have three numbers - extract and put on the list
                val digitRegexMatch = Regex("(\\d+) (\\d+) (\\d+)").matchEntire(line)
                val destRangeStart = digitRegexMatch!!.groups[1]!!.value.toLong()
                val sourceRangeStart = digitRegexMatch.groups[2]!!.value.toLong()
                val range = digitRegexMatch.groups[3]!!.value.toLong()

                dataToProcess[sourceRangeStart] = LongArray(2)
                dataToProcess[sourceRangeStart]!![0] = destRangeStart
                dataToProcess[sourceRangeStart]!![1] = range
            }
        }
        processData(dataToProcess, mapOfMaps, mapName)

        val soils = seeds.map { lookupMap("seed-to-soil", it, mapOfMaps) }
        val fertilizers = soils.map { lookupMap("soil-to-fertilizer", it, mapOfMaps) }
        val waters = fertilizers.map { lookupMap("fertilizer-to-water", it, mapOfMaps) }
        val lights = waters.map { lookupMap("water-to-light", it, mapOfMaps) }
        val temperatures = lights.map { lookupMap("light-to-temperature", it, mapOfMaps) }
        val humidities = temperatures.map { lookupMap("temperature-to-humidity", it, mapOfMaps) }
        val locations = humidities.map { lookupMap("humidity-to-location", it, mapOfMaps) }

        return locations.min()
    }

    fun getLocationForSeed(nextSeed: Long, mapOfMaps: MutableMap<String, Map<Long, Long>>): Long {
        val soil = lookupMap("seed-to-soil", nextSeed, mapOfMaps)
        val fertilizer = lookupMap("soil-to-fertilizer", soil, mapOfMaps)
        val water = lookupMap("fertilizer-to-water", fertilizer, mapOfMaps)
        val light = lookupMap("water-to-light", water, mapOfMaps)
        val temp = lookupMap("light-to-temperature", light, mapOfMaps)
        val humidity = lookupMap("temperature-to-humidity", temp, mapOfMaps)
        return lookupMap("humidity-to-location", humidity, mapOfMaps)
    }

    fun part2(input: List<String>): Long {

        val originalSeeds =
            input[0].split("seeds:")[1].split("\\s+".toRegex()).filterNot { it.isBlank() }.map { it.toLong() }

        val mapOfMaps = mutableMapOf<String, Map<Long, Long>>()
        var mapName = ""
        val dataToProcess = mutableMapOf<Long, LongArray>()
        for (line in input.subList(1, input.size)) {
            val regexMatch = Regex("(.*) map:").matchEntire(line)
            if (regexMatch != null) {
                mapName = regexMatch.groups[1]!!.value
                mapOfMaps[mapName] = mutableMapOf(0L to 0L)
            } else if (line.isBlank()) {
                processData(dataToProcess, mapOfMaps, mapName)
            } else {
                // should have three numbers - extract and put on the list
                val digitRegexMatch = Regex("(\\d+) (\\d+) (\\d+)").matchEntire(line)
                val destRangeStart = digitRegexMatch!!.groups[1]!!.value.toLong()
                val sourceRangeStart = digitRegexMatch.groups[2]!!.value.toLong()
                val range = digitRegexMatch.groups[3]!!.value.toLong()

                dataToProcess[sourceRangeStart] = LongArray(2)
                dataToProcess[sourceRangeStart]!![0] = destRangeStart
                dataToProcess[sourceRangeStart]!![1] = range
            }
        }
        processData(dataToProcess, mapOfMaps, mapName)

        var nextIndex = 0
        var nextSeed: Long
        var lowestLocation = Long.MAX_VALUE

        while (nextIndex < originalSeeds.size) {
            val nextStart = originalSeeds[nextIndex]
            val nextRange = originalSeeds[nextIndex + 1]
            val end = nextStart + nextRange

            println("Processing seeds starting with ID $nextStart and going to $end")

            nextSeed = nextStart
            while (nextSeed < end) {

                if ((nextSeed % 500000).toInt() == 0) {
                    println("Processing seed $nextSeed")
                }

                val location = getLocationForSeed(nextSeed, mapOfMaps)

                if (location < lowestLocation) {
                    println("Updating lowestLocation from $lowestLocation to $location")
                    lowestLocation = location
                }

                nextSeed++
            }

            nextIndex += 2
        }

        return lowestLocation - 1
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)

    val part2Sample = part2(testInput)
    println("Part one sample answer is $part2Sample")
    check(part2Sample == 46L)

    val input = readInput("Day05")
    print("Part one answer is ") // ?
    part1(input).println()
    print("Part two answer is ") // ?
    part2(input).println()
}