fun main() {
    fun part1(input: List<String>): Int {

        for(i in input.indices) {
            
        }
        var gamma = 0
        var epsilon = 0

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    assert(part1(testInput) == 0)
    assert(part2(testInput) == 0)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
