import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min

fun main() {
    fun part1(input: List<String>): Int {

        fun fuel(input: List<Int>, position: Int): Int {
            return input.map{abs(it - position)}.sum()
        }

        var pos = input[0].split(',').map(String::toInt).sorted()

        var res = 0
        var minimum = Int.MAX_VALUE
        for(i in pos.minOf { it }..pos.maxOf { it }) {
            val current = fuel(pos, i)
            if (current < minimum) {
                minimum = current
                res = i
            }
        }
        return fuel(pos,res)
    }



    fun part2(input: List<String>): Int {
        fun fuel(input: List<Int>, position: Int): Int {
            return input.map{
                var n = abs(it - position)
                n*(1+n)/2
            }.sum()
        }

        var pos = input[0].split(',').map(String::toInt).sorted()

        var res = 0
        var minimum = Int.MAX_VALUE
        for(i in pos.minOf { it }..pos.maxOf { it }) {
            val current = fuel(pos, i)
            if (current < minimum) {
                minimum = current
                res = i
            }
        }
        return fuel(pos,res)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    assert(part1(testInput) == 37)
    assert(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
