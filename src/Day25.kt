import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Exception
import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue


fun main() {
    fun destination(map: Array<Array<Char>>, i: Int, j: Int): Pair<Int, Int>? {
        when (map[i][j]) {
            '>' -> {
                if (j < (map[i].size - 1) && map[i][j+1] == '.') return Pair(i, j+1)
                if (j == (map[i].size - 1) && map[i][0] == '.') return Pair(i, 0)
            }
            'v' -> {
                if (i < (map.size - 1) && map[i+1][j] == '.') return Pair(i+1, j)
                if (i == (map.size - 1) && map[0][j] == '.') return Pair(0, j)
            }
        }
        return null
    }
    fun cloneMap(map: Array<Array<Char>>): Array<Array<Char>> {
        var newMap = Array(map.size) {Array(map[0].size) { '.' } }
        for(i in map.indices) {
            for (j in map[i].indices) {
                newMap[i][j] = map[i][j]
            }
        }
        return newMap
    }
    fun move(map: Array<Array<Char>>): Pair<Array<Array<Char>>, Boolean> {
        var moved = false
        var newMap = cloneMap(map)
        for(i in map.indices) {
            for(j in map[i].indices) {
                if (map[i][j] == '>') {
                    var dest = destination(map, i, j)
                    if (dest != null) {
                        moved = true
                        newMap[i][j] = '.'
                        newMap[dest.first][dest.second] = '>'
                    }
                }
            }
        }
        var newMapV = cloneMap(newMap)
        for(i in map.indices) {
            for(j in map[i].indices) {
                if (newMap[i][j] == 'v') {
                    var dest = destination(newMap, i, j)
                    if (dest != null) {
                        moved = true
                        newMapV[i][j] = '.'
                        newMapV[dest.first][dest.second] = 'v'
                    }
                }
            }
        }
        return Pair(newMapV,moved)
    }
    fun part1(input: List<String>): Int {
        var map = Array(input.size) {Array(input[0].length) { '.' } }
        for(i in input.indices) {
            for(j in input[i].indices) {
                map[i][j] = input[i][j]
            }
        }
        var step = 0
        var moved = true
        while (moved) {
            step++
            var result = move(map)
            moved = result.second
            map = result.first
        }
        return step
    }



    fun part2(input: List<String>): Int {

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day25_test")
    assert(part1(testInput) == 58)
    assert(part2(testInput) == 0)

    val input = readInput("Day25")
    println(part1(input))
    println(part2(input))
}
