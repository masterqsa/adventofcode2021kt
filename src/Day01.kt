fun main() {
    fun part1(input: List<String>): Int {
        var nums = input.map { it.toInt() };
        var c = 0;
        for(i in 0..(nums.size-2)) {
            if (nums[i+1] > nums[i])
            {
                c++
            }
        }

        return c
    }

    fun part2(input: List<String>): Int {
        var nums = input.map { it.toInt() };
        var c = 0;
        var prev = nums[0] + nums[1] + nums [2]
        for(i in 1..(nums.size-3)) {
            var curr = prev-nums[i-1]+nums[i+2]
            if (curr > prev)
            {
                c++
            }
            prev = curr
        }

        return c
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    assert(part1(testInput) == 7)
    assert(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
