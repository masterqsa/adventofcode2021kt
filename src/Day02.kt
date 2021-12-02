fun main() {
    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        for(i in input.indices) {
            var command = input[i].substringBefore(' ')
            var value = input[i].substringAfter(' ').toInt()
            when (command){
                "up" -> y-= value
                "down" -> y+= value
                "forward" -> x+=value
            }
        }

        return x * y
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var x = 0
        var y = 0
        for(i in input.indices) {
            var command = input[i].substringBefore(' ')
            var value = input[i].substringAfter(' ').toInt()
            when (command){
                "up" -> aim-= value
                "down" -> aim+= value
                "forward" -> {
                    x+=value
                    y+= aim*value
                }
            }
        }

        return x * y
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    assert(part1(testInput) == 150)
    assert(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
