import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var cost = mapOf<Char, Int>(')' to 3,']' to 57,'}' to 1197,'>' to 25137)
        var matching = mapOf<Char, Char>(')' to '(',']' to '[','}' to '{','>' to '<')
        var stack = Stack<Char>()
        var total = 0
        for(s in input) {
            for(i in s.indices) {
                var c = s[i]
                if (!matching.keys.contains(c)) {
                    stack.push(c)
                } else {
                    if (stack.pop() != matching[c]) {
                        total+=cost[c]!!
                        break
                    }
                }
            }
        }
        return total
    }

    fun part2(input: List<String>): Long {
        var cost = mapOf<Char, Int>('(' to 1,'[' to 2,'{' to 3,'<' to 4)
        var matching = mapOf<Char, Char>(')' to '(',']' to '[','}' to '{','>' to '<')


        var scores = mutableListOf<Long>()
        for(s in input) {
            var stack = Stack<Char>()
            var total = 0L
            var failed = false
            for(i in s.indices) {
                var c = s[i]
                if (!matching.keys.contains(c)) {
                    stack.push(c)
                } else {
                    if (stack.pop() != matching[c]) {
                        failed = true
                        break
                    }
                }
            }
            if (!failed) {
                while(!stack.empty()) {
                    total*=5
                    total+=cost[stack.pop()]!!
                }
                scores.add(total)
            }
        }
        scores.sort()
        return scores[scores.size/2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    assert(part1(testInput) == 26397)
    assert(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
