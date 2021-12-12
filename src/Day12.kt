import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        fun navigate(m: MutableMap<String, MutableSet<String>>, current: String, visited: MutableSet<String>) : Int {
            if (!m.containsKey(current)) return 0
            if (current == "end") {
                return 1
            }
            var routes = 0
            for(n in m[current]!!) {
                if (!visited.contains(n)) {
                    var v = visited.map { a -> a }.toMutableSet()
                    if (n.lowercase(Locale.getDefault()) == n) v.add(n)
                    routes+=navigate(m, n, v)
                }
            }
            return routes
        }
        var m = mutableMapOf<String, MutableSet<String>>()
        for(s in input) {
            var from = s.substringBefore('-')
            var to = s.substringAfter('-')
            if (m.containsKey(from)) {
                m[from]?.add(to)
            } else {
                m[from] = mutableSetOf<String>(to)
            }
            if (m.containsKey(to)) {
                m[to]?.add(from)
            } else {
                m[to] = mutableSetOf<String>(from)
            }
        }
        var r = navigate(m, "start", mutableSetOf<String>("start"))
        return r
    }



    fun part2(input: List<String>): Int {
        fun navigate(m: MutableMap<String, MutableSet<String>>,
                     current: String,
                     visited: MutableSet<String>,
                     twice: Boolean,
        ) : Int {
            if (current == "end") {
                return 1
            }
            if (!m.containsKey(current)) return 0

            var routes = 0
            for(n in m[current]!!) {
                var v = visited.map { a -> a }.toMutableSet()
                if (!visited.contains(n)) {
                    if (n.lowercase(Locale.getDefault()) == n) {
                        v.add(n)
                    }
                    routes+=navigate(m, n, v, twice)
                } else {
                    if (!twice && n != "start" && n != "end") {
                        routes+=navigate(m, n, v, true)
                    }
                }
            }
            return routes
        }
        var m = mutableMapOf<String, MutableSet<String>>()
        for(s in input) {
            var from = s.substringBefore('-')
            var to = s.substringAfter('-')
            if (m.containsKey(from)) {
                m[from]?.add(to)
            } else {
                m[from] = mutableSetOf<String>(to)
            }
            if (m.containsKey(to)) {
                m[to]?.add(from)
            } else {
                m[to] = mutableSetOf<String>(from)
            }
        }
        var r = navigate(m, "start", mutableSetOf<String>("start"), false)
        return r
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    assert(part1(testInput) == 10)
    assert(part2(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
