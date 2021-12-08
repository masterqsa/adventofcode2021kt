import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.min

fun main() {
    fun part1(input: List<String>): Int {
        var second = input.map{ it.substringAfter(" | ")}
        var count = 0
        var known = setOf<Int>(2,3,4,7)
        for(s in second) {
            var split = s.split(' ')
            count+= split.map { if (it.length in known) 1 else 0}.sum()
        }

        return count
    }



    fun part2(input: List<String>): Int {
        fun sortString(s: String): String {
            return s.toCharArray().sorted().joinToString("")
        }
        var count = 0
        for(s in input) {
            var first =  s.substringBefore(" | ")
            var second = s.substringAfter(" | ")
            var split = first.split(' ').sortedBy { it.length }.map { sortString(it) }
            var m = mutableMapOf<String, Int>()
            var code1 = split[0]
            m.put(code1, 1)
            var code7 = split[1]
            m.put(code7, 7)
            var code4 = split[2]
            m.put(code4, 4)
            var code8 = split[9]
            m.put(code8, 8)
            var code9 = ""
            for(i in 6..8) {
                if (code4.toCharArray().all { split[i].toCharArray().contains(it) }) {
                    m.put(split[i], 9)
                    code9 = split[i]
                    break
                }
            }
            var code3 = ""
            for(i in 3..5) {
                if (code1.toCharArray().all { split[i].toCharArray().contains(it) }

                ) {
                    m.put(split[i], 3)
                    code3 = split[i]
                    break
                }
            }
            var code0 = ""
            for(i in 6..8) {
                if (split[i] != code9
                    && code7.toCharArray().all { split[i].toCharArray().contains(it) }) {
                    m.put(split[i], 0)
                    code0 = split[i]
                    break
                }
            }
            var code5 = ""
            for(i in 3..5) {
                if (split[i] == code3) continue
                var match = split[i].toCharArray().map { if (code9.toCharArray().contains(it)) 1 else 0 }.sum()
                if (match == 5) {
                    m.put(split[i], 5)
                    code5 = split[i]
                    break
                }
            }
            var code6 = ""
            for(i in 6..8) {
                if (split[i] == code0 || split[i] == code9) continue
                m.put(split[i], 6)
                code6 = split[i]
            }
            var code2 = ""
            for(i in 3..5) {
                if (split[i] == code3 || split[i] == code5) continue
                m.put(split[i], 2)
                code2 = split[i]
            }

            var nums = second.split(' ').map { sortString(it) }
                .map { m[it]?:0 }
            var current = (1000*nums[0]+100*nums[1]+10*nums[2]+nums[3])
            count+= current
        }

        return count
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    assert(part1(testInput) == 26)
    assert(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
