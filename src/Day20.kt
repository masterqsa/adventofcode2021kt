import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>): Int {
        fun getIndex(map: Array<Array<Boolean>>,bg: Boolean,  i: Int, j: Int): Int {
            var s = ""
            for(x in i-1..i+1) {
                for(y in j-1..j+1) {
                    if (x < 0 || y < 0 || x >= map.size || y >= map.size) {
                        s+= if (bg) "1" else "0"
                    } else {
                        s+= if (map[x][y]) "1" else "0"
                    }
                }
            }
            return s.toInt(2)
        }
        fun transform(map: Array<Array<Boolean>>, rules: CharArray, bg: Boolean): Array<Array<Boolean>>{
            var newMap = Array(map.size) { Array(map.size) { bg } }
            for(i in map.indices) {
                for(j in map[i].indices) {
                    newMap[i][j] = rules[getIndex(map, bg, i, j)] == '#'
                }
            }
            return newMap
        }
        var offset = 2
        var rules = input[0].toCharArray()
        var size = input.size-2 + offset * 2
        var map = Array(size) { Array(size) { false } }
        for(i in 2 until input.size) {
            for(j in input[i].indices) {
                map[offset+i-2][offset+j] = (input[i][j] == '#')
            }
        }

        var bg = false
        for(s in 1..2) {
            map = transform(map, rules, bg)
            bg = if (bg) {
                rules[511] == '#'
            } else {
                rules[0] == '#'
            }
        }

        var count = 0
        for(i in map.indices) {
            for(j in map[i].indices)
                if (map[i][j]) count++
        }
        return count
    }



    fun part2(input: List<String>): Int {
        fun getIndex(map: Array<Array<Boolean>>,bg: Boolean,  i: Int, j: Int): Int {
            var s = ""
            for(x in i-1..i+1) {
                for(y in j-1..j+1) {
                    if (x < 0 || y < 0 || x >= map.size || y >= map.size) {
                        s+= if (bg) "1" else "0"
                    } else {
                        s+= if (map[x][y]) "1" else "0"
                    }
                }
            }
            return s.toInt(2)
        }
        fun transform(map: Array<Array<Boolean>>, rules: CharArray, bg: Boolean): Array<Array<Boolean>>{
            var newMap = Array(map.size) { Array(map.size) { bg } }
            for(i in map.indices) {
                for(j in map[i].indices) {
                    newMap[i][j] = rules[getIndex(map, bg, i, j)] == '#'
                }
            }
            return newMap
        }
        var offset = 50
        var rules = input[0].toCharArray()
        var size = input.size-2 + offset * 2
        var map = Array(size) { Array(size) { false } }
        for(i in 2 until input.size) {
            for(j in input[i].indices) {
                map[offset+i-2][offset+j] = (input[i][j] == '#')
            }
        }

        var bg = false
        for(s in 1..50) {
            map = transform(map, rules, bg)
            bg = if (bg) {
                rules[511] == '#'
            } else {
                rules[0] == '#'
            }
        }

        var count = 0
        for(i in map.indices) {
            for(j in map[i].indices)
                if (map[i][j]) count++
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    assert(part1(testInput) == 35)
    assert(part2(testInput) == 3351)

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}
