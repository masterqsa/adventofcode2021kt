import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var steps = 10
        var m = mutableMapOf<String, String>()
        var current = input[0]
        for(i in 2 until input.size) {
            m.put(input[i].substringBefore(" -> "), input[i].substringAfter(" -> "))
        }
        for(i in 1..steps) {
            var newString = current[0].toString()
            for(i in 1 until current.length) {
                var key = current.substring(i-1, i+1)
                if(m.containsKey(key)) {
                    newString+=(m[key]+current[i].toString())
                } else {
                    newString+=current[i].toString()
                }
            }
            current = newString
            //println(current)
        }
        var counts = mutableMapOf<Char, Int>()
        var maxC = 0
        var minC = current.length
        for(c in current) {
            counts[c] = (counts[c]?:0) + 1
        }
        for(c in counts.keys) {
            if (counts[c]!! > maxC) {
                maxC = counts[c]!!
            }
            if (counts[c]!! < minC) {
                minC = counts[c]!!
            }
        }
        return maxC - minC
    }



    fun part2(input: List<String>): Long {
        var steps = 40
        var m = mutableMapOf<String, String>()
        var current = input[0]
        for(i in 2 until input.size) {
            m.put(input[i].substringBefore(" -> "), input[i].substringAfter(" -> "))
        }
        var pairCounts = mutableMapOf<String, Long>()
        for(i in 1 until current.length) {
            var key = current.substring(i - 1, i + 1)
            pairCounts[key] = (pairCounts[key]?:0L) + 1L
        }

        for(i in 1..steps) {
            var newPairCounts = mutableMapOf<String, Long>()
            for(key in pairCounts.keys) {
                if (m.containsKey(key)) {
                    var leftKey = key[0].toString()+m[key]!!
                    var rightKey = m[key]!!+key[1].toString()
                    newPairCounts[leftKey] = (newPairCounts[leftKey]?:0L) + pairCounts[key]!!
                    newPairCounts[rightKey] = (newPairCounts[rightKey]?:0L) + pairCounts[key]!!
                } else {
                    newPairCounts[key] = (newPairCounts[key]?:0L) + pairCounts[key]!!
                }
            }
            pairCounts = newPairCounts
        }
        var counts = mutableMapOf<Char, Long>()
        var maxC = 0L
        var minC = Long.MAX_VALUE
        for(key in pairCounts.keys) {
            counts[key[0]] = (counts[key[0]]?:0L) + (pairCounts[key]?:0)
        }
        counts[current[current.length-1]] = counts[current[current.length-1]]!! + 1

        for(c in counts.keys) {
            var cnt = counts[c]!!
            if (cnt > maxC) {
                maxC = cnt
            }
            if (cnt < minC) {
                minC = cnt
            }
        }
        return maxC - minC

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    assert(part1(testInput) == 1588)
    assert(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
