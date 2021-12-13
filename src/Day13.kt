import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var maxX = 0
        var maxY = 0
        var cuts = 0
        for(i in input.indices) {
            if (input[i].isEmpty()) {
                cuts = i+1
                break
            }
            var x = input[i].substringBefore(',').toInt()
            var y = input[i].substringAfter(',').toInt()
            if (x > maxX) maxX = x
            if (y > maxY) maxY = y
        }
        var m = Array<Array<Boolean>>(2*maxX+1) { Array<Boolean>(2*maxY+1) { false } }
        for(s in input) {
            if (s.isEmpty()) break
            var x = s.substringBefore(',').toInt()
            var y = s.substringAfter(',').toInt()
            m[x][y] = true
        }
        for(i in cuts until input.size)
        {

        }
        var fold = input[cuts].replace("fold along ", "")
        var op = fold.substringBefore('=')
        var arg = fold.substringAfter('=').toInt()
        var count = 0
        if (op == "x") {
            for(x in (arg+1)..maxX) {
                for(y in 0..maxY) {
                    if (m[x][y]) m[2*arg - x][y] = true
                }
            }
            maxX = arg-1
        } else {
            for(x in 0..maxX) {
                for(y in (arg+1)..maxY) {
                    if (m[x][y]) m[x][2*arg - y] = true
                }
            }
            maxY = arg-1
        }

        for(y in 0..maxY) {
            for(x in 0..maxX) {
                if (m[x][y]) {
                    count++
                }
            }
        }

        /*println()
        for(y in 0..maxY) {
            for(x in 0..maxX) {
                if (m[x][y]) {
                    print('#')
                } else {
                    print('.')
                }
            }
            println()
        }

         */

        return count
    }



    fun part2(input: List<String>): Int {
        var maxX = 0
        var maxY = 0
        var cuts = 0
        for(i in input.indices) {
            if (input[i].isEmpty()) {
                cuts = i+1
                break
            }
            var x = input[i].substringBefore(',').toInt()
            var y = input[i].substringAfter(',').toInt()
            if (x > maxX) maxX = x
            if (y > maxY) maxY = y
        }
        var m = Array<Array<Boolean>>(2*maxX+1) { Array<Boolean>(2*maxY+1) { false } }
        for(s in input) {
            if (s.isEmpty()) break
            var x = s.substringBefore(',').toInt()
            var y = s.substringAfter(',').toInt()
            m[x][y] = true
        }
        var count = 0
        for(i in cuts until input.size) {


            var fold = input[i].replace("fold along ", "")
            var op = fold.substringBefore('=')
            var arg = fold.substringAfter('=').toInt()

            if (op == "x") {
                for (x in (arg + 1)..maxX) {
                    for (y in 0..maxY) {
                        if (m[x][y]) m[2 * arg - x][y] = true
                    }
                }
                maxX = arg - 1
            } else {
                for (x in 0..maxX) {
                    for (y in (arg + 1)..maxY) {
                        if (m[x][y]) m[x][2 * arg - y] = true
                    }
                }
                maxY = arg - 1
            }
        }

        for(y in 0..maxY) {
            for(x in 0..maxX) {
                if (m[x][y]) {
                    count++
                }
            }
        }

        println()
        for(y in 0..maxY) {
            for(x in 0..maxX) {
                if (m[x][y]) {
                    print('#')
                } else {
                    print('.')
                }
            }
            println()
        }



        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    assert(part1(testInput) == 17)
    assert(part2(testInput) == 16)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
