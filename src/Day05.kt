import java.lang.Math

fun main() {
    fun part1(input: List<String>): Int {
        var points = mutableMapOf<Pair<Int, Int>, Int>()
        var count = 0
        for(s in input) {
            var from = s.substringBefore(" -> ").split(',').map(String::toInt)
            var to = s.substringAfter(" -> ").split(',').map(String::toInt)
            if (from[0] == to[0] || from[1] == to[1]) {
                for (x in Math.min(from[0], to[0])..Math.max(from[0], to[0])) {
                    for (y in Math.min(from[1], to[1])..Math.max(from[1], to[1])) {
                        if (points.containsKey(Pair(x, y))) {
                            if (points[Pair(x, y)] == 1) count++
                            points[Pair(x, y)] = 1 + points[Pair(x, y)]!!
                        } else {
                            points[Pair(x, y)] = 1
                        }
                    }
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var points = mutableMapOf<Pair<Int, Int>, Int>()
        var count = 0
        for(s in input) {
            var from = s.substringBefore(" -> ").split(',').map(String::toInt)
            var to = s.substringAfter(" -> ").split(',').map(String::toInt)
            if (from[0] == to[0] || from[1] == to[1]) {
                for (x in Math.min(from[0], to[0])..Math.max(from[0], to[0])) {
                    for (y in Math.min(from[1], to[1])..Math.max(from[1], to[1])) {
                        if (points.containsKey(Pair(x, y))) {
                            if (points[Pair(x, y)] == 1) count++
                            points[Pair(x, y)] = 1 + points[Pair(x, y)]!!
                        } else {
                            points[Pair(x, y)] = 1
                        }
                    }
                }
            } else {
                var dx = if (to[0]-from[0] > 0) 1 else -1
                var dy = if (to[1]-from[1] > 0) 1 else -1
                var x = from[0]
                var y = from[1]
                while(x != to[0]+dx) {
                    if (points.containsKey(Pair(x, y))) {
                        if (points[Pair(x, y)] == 1) count++
                        points[Pair(x, y)] = 1 + points[Pair(x, y)]!!
                    } else {
                        points[Pair(x, y)] = 1
                    }
                    x+=dx
                    y+=dy
                }
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    assert(part1(testInput) == 5)
    assert(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
