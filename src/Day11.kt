import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        fun flash(m: Array<Array<Int>>, i: Int, j: Int): Int {
            m[i][j] = 0
            var c = 1
            for(dx in -1..1) {
                for(dy in -1..1) {
                    if(i+dx in 0..9 && j+dy in 0..9 && !(dx==0 && dy==0)) {
                        if (m[i+dx][j+dy] in 1..9) {
                            m[i+dx][j+dy]++
                        }
                        if (m[i+dx][j+dy] == 10) {
                            c += flash(m, i + dx, j + dy)
                        }
                    }
                }
            }
            return c
        }
        val steps = 100
        var flashes = 0
        var m = Array<Array<Int>>(10) { Array<Int>(10) { 10 } }
        for (i in input.indices) {
            for (j in input[i].indices) {
                m[i][j] = input[i][j] - '0'
            }
        }
        for (s in 1..steps) {
            for (i in m.indices) {
                for (j in m[i].indices) {
                    m[i][j]++
                }
            }

            for (i in m.indices) {
                for (j in m[i].indices) {
                    if (m[i][j]==10) {
                        flashes += flash(m, i, j)
                    }
                }
            }

        }
        return flashes
    }



    fun part2(input: List<String>): Int {
        fun flash(m: Array<Array<Int>>, i: Int, j: Int): Int {
            m[i][j] = 0
            var c = 1
            for(dx in -1..1) {
                for(dy in -1..1) {
                    if(i+dx in 0..9 && j+dy in 0..9 && !(dx==0 && dy==0)) {
                        if (m[i+dx][j+dy] in 1..9) {
                            m[i+dx][j+dy]++
                        }
                        if (m[i+dx][j+dy] == 10) {
                            c += flash(m, i + dx, j + dy)
                        }
                    }
                }
            }
            return c
        }
        val steps = 10000
        var flashes = 0
        var m = Array<Array<Int>>(10) { Array<Int>(10) { 10 } }
        for (i in input.indices) {
            for (j in input[i].indices) {
                m[i][j] = input[i][j] - '0'
            }
        }
        for (s in 1..steps) {
            for (i in m.indices) {
                for (j in m[i].indices) {
                    m[i][j]++
                }
            }

            for (i in m.indices) {
                for (j in m[i].indices) {
                    if (m[i][j]==10) {
                        flashes += flash(m, i, j)
                    }
                }
            }

            var all = true
            for (i in m.indices) {
                for (j in m[i].indices) {
                    if (m[i][j]!=0) {
                        all = false
                    }
                }
            }
            if (all) {
                return s
            }
        }
        return flashes
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    assert(part1(testInput) == 1656)
    assert(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
