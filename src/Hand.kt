data class Hand(val hand: String, val bid: Long) {

    fun getRankPartOne(): Int {
        val handAsSet = hand.toList().toSet()
        if (handAsSet.size == 1) {
            return FIVE_OF_A_KIND
        } else if (handAsSet.size == 2) {
            return if (containsAPair(hand)) {
                FULL_HOUSE
            } else {
                FOUR_OF_A_KIND
            }
        } else if (handAsSet.size == 3) {
            return if (containsAPair(hand)) {
                TWO_PAIR
            } else {
                THREE_OF_A_KIND
            }
        } else if (containsAPair(hand)) {
            return PAIR
        }
        return HIGH_CARD
    }

    fun getRankPartTwo(): Int {
        if (!hand.contains('J')) {
            return getRankPartOne()
        }

        val handAsSet = hand.toList().toSet()
        val numberOfJokers = getCountOfCardInHand(hand, 'J')

        if (handAsSet.size <= 2) {
            return FIVE_OF_A_KIND
        } else if (handAsSet.size == 5) {
            return PAIR
        } else if (handAsSet.size == 4) {
            return THREE_OF_A_KIND
            // handAsSet.size == 3
            // options:
            // [JJJ A] B -> 4 of a kind
            // [JJ AA] B -> 4 of a kind
            // [J AAA] B -> 4 of a kind
            // [J AA] BB -> full house
        } else if (numberOfJokers == 1 && containsTwoPairs(hand)) {
            return FULL_HOUSE
        } else {
            return FOUR_OF_A_KIND
        }
    }

    private fun containsAPair(hand: String): Boolean {
        return hand.any { getCountOfCardInHand(hand, it) == 2 }
    }

    private fun containsTwoPairs(hand: String): Boolean {
        val pairs = hand.filter { getCountOfCardInHand(hand, it) == 2 }
        return pairs.toSet().size == 2
    }

    private fun getCountOfCardInHand(hand: String, card: Char): Int {
        return hand.count { it == card }
    }

    fun getCard1(part1: Boolean): Int {
        return getCard(hand[0], part1)
    }

    fun getCard2(part1: Boolean): Int {
        return getCard(hand[1], part1)
    }

    fun getCard3(part1: Boolean): Int {
        return getCard(hand[2], part1)
    }

    fun getCard4(part1: Boolean): Int {
        return getCard(hand[3], part1)
    }

    fun getCard5(part1: Boolean): Int {
        return getCard(hand[4], part1)
    }

    private fun getCard(char: Char, part1: Boolean): Int {
        return when (char) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> if (part1) 11 else 1
            'T' -> 10
            else -> "$char".toInt()
        }
    }

    companion object {
        const val FIVE_OF_A_KIND = 7
        const val FOUR_OF_A_KIND = 6
        const val FULL_HOUSE = 5
        const val THREE_OF_A_KIND = 4
        const val TWO_PAIR = 3
        const val PAIR = 2
        const val HIGH_CARD = 1
    }
}