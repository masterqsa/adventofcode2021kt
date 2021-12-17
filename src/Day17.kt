import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>): Int {
        var xrange = input[0].substringAfter("target area: x=").substringBefore(",")
        var yrange = input[0].substringAfter("target area: x=").substringAfter(", y=")
        var minX = xrange.substringBefore("..").toInt()
        var maxX = xrange.substringAfter("..").toInt()
        var minY = yrange.substringBefore("..").toInt()
        var maxY = yrange.substringAfter("..").toInt()
        return ((minY+1)*(minY+1)-minY-1)/2
    }



    fun part2(input: List<String>): Int {
        var xrange = input[0].substringAfter("target area: x=").substringBefore(",")
        var yrange = input[0].substringAfter("target area: x=").substringAfter(", y=")
        var minX = xrange.substringBefore("..").toInt()
        var maxX = xrange.substringAfter("..").toInt()
        var minY = yrange.substringBefore("..").toInt()
        var maxY = yrange.substringAfter("..").toInt()

        var count = 0
        for(vxs in 1..maxX) {
            for(vys in minY..(-minY)) {
                var vx = vxs
                var vy = vys
                var x = 0
                var y = 0
                while(x <= maxX && y >= minY) {
                    x+=vx
                    y+=vy
                    if (vx > 0) vx--
                    vy--
                    if (x in minX..maxX && y in minY..maxY) {
                        count++
                        break
                    }
                }
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    assert(part1(testInput) == 45)
    assert(part2(testInput) == 112)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}
