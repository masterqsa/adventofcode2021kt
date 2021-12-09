import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        fun checkPoint(m: Array<Array<Int>>, i: Int, j: Int): Boolean {
            if (i > 0) {
                if (m[i-1][j] <= m[i][j]) return false
            }
            if (j > 0) {
                if (m[i][j-1] <= m[i][j]) return false
            }
            if (i < m.size-1) {
                if (m[i+1][j] <= m[i][j]) return false
            }
            if (j < m[0].size-1) {
                if (m[i][j+1] <= m[i][j]) return false
            }
            return true
        }
        var m = Array<Array<Int>>(input.size) { _ -> Array<Int>(input[0].length) { _ -> 0 }}
        for(i in input.indices) {
            for(j in input[i].indices) {
                m[i][j] = input[i][j]-'0'
            }
        }
        var count = 0
        for(i in input.indices) {
            for (j in input[i].indices) {
                if (checkPoint(m, i, j)) {
                    count+=(m[i][j]+1)
                }
            }
        }

        return count
    }



    fun part2(input: List<String>): Int {
        fun mark(m: Array<Array<Int>>, i: Int, j: Int, current: Int): Unit {
            if (i > 0) {
                if (m[i-1][j] in 0..8)  {
                    m[i-1][j] = current
                    mark(m, i-1, j, current)
                }
            }
            if (j > 0) {
                if (m[i][j-1] in 0..8) {
                    m[i][j-1] = current
                    mark(m, i, j-1, current)
                }
            }
            if (i < m.size-1) {
                if (m[i+1][j] in 0..8) {
                    m[i+1][j] = current
                    mark(m, i+1, j, current)
                }
            }
            if (j < m[0].size-1) {
                if (m[i][j+1] in 0..8) {
                    m[i][j+1] = current
                    mark(m, i, j+1, current)
                }
            }
        }
        var m = Array<Array<Int>>(input.size) { _ -> Array<Int>(input[0].length) { _ -> 0 }}
        for(i in input.indices) {
            for(j in input[i].indices) {
                m[i][j] = input[i][j]-'0'
            }
        }
        var current = -1

        for(i in input.indices) {
            for (j in input[i].indices) {
                if (m[i][j] in 0..8) {
                    m[i][j] = current
                    mark(m, i, j, current)
                    current--
                }
            }
        }
        var count = Array<Int>(-current) { _ -> 0 }
        for(i in input.indices) {
            for (j in input[i].indices) {
                if (m[i][j] < 0) {
                    count[-m[i][j]-1]++
                }
            }
        }
        var ordered = count.sorted().reversed()
        var product = 1
        for(i in 0..2) {
            product*=ordered[i]
        }
        return product
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    assert(part1(testInput) == 15)
    assert(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
