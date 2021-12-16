import java.lang.Math
import java.lang.Math.*
import java.util.*

fun main() {
    class BinSegment(var ver: Int, var type: Int, var parent: BinSegment?) {
        var children: MutableList<BinSegment> = mutableListOf()
        var literal: String? = null
        fun value(): Long {
            return literal?.toLong(2) ?: calculate()
        }
        fun calculate(): Long {
            when (type) {
                4 -> return value()
                0 -> {
                    // sum
                    return children.map { it.value() }.sum()
                }
                1 -> {
                    // product
                    return children.map { it.value() }.reduce{ product, element -> product*element }
                }
                2 -> {
                    // minimum
                    return children.map { it.value() }.reduce { minimum, it -> min(minimum, it) }
                }
                3 -> {
                    // maximum
                    return children.map { it.value() }.reduce { maximum, it -> max(maximum, it) }
                }
                5 -> {
                    // sub 1 is greater than sub 2
                    return if (children[0].value() > children[1].value()) 1 else 0
                }
                6 -> {
                    // sub 1 is less than sub 2
                    return if (children[0].value() < children[1].value()) 1 else 0
                }
                7 -> {
                    // sub 1 equal to sub 2
                    return if (children[0].value() == children[1].value()) 1 else 0
                }

            }
            return 0
        }
        fun addSegment(s: BinSegment) {
            children.add(s)
        }
        fun addLiteral(l: String) {
            literal = l
        }
    }
    fun part1(input: List<String>): Int {
        fun readSegment(s: String, startPos: Int, current: BinSegment): Int {
            var pos = startPos
            var v = s.substring(pos, pos+3).toInt(2)
            pos+=3
            var t = s.substring(pos, pos+3).toInt(2)
            pos+=3
            var seg = BinSegment(v, t, current)
            current.addSegment(seg)
            if (seg.type == 4) {
                var literalString = ""
                while(s.substring(pos, pos+1) == "1") {
                    var tmpString = s.substring(pos+1, pos+5)
                    pos+=5
                    literalString+=tmpString
                }
                var tmpString = s.substring(pos+1, pos+5)
                pos+=5
                literalString+=tmpString
                seg.addLiteral(literalString)
            } else {
                var id = s.substring(pos, pos+1)
                pos+=1
                if (id == "0") {
                    // read next 15 bits for length
                    var l = s.substring(pos, pos+15).toInt(2)
                    pos+=15
                    var rightLimit = pos + l
                    while(pos < rightLimit) {
                        pos = readSegment(s, pos, seg)
                    }
                } else {
                    // read next 11 bits for segment count
                    var cnt = s.substring(pos, pos+11).toInt(2)
                    pos+=11
                    for(i in 1..cnt) {
                        pos = readSegment(s, pos, seg)
                    }
                }
            }
            return pos
        }
        fun sumVersions(root: BinSegment): Int {
            if (root.children.isEmpty()) return root.ver

            var localSum = root.ver
            for (c in root.children) {
                localSum += sumVersions(c)
            }
            return localSum
        }
        var s = ""
        for(c in input[0]) {
            s += c.toString().toInt(16).toString(2).padStart(4, '0')
        }
        var root = BinSegment(0,0, null)

        readSegment(s, 0, root)

        var sumVer = sumVersions(root)
        return sumVer
    }



    fun part2(input: List<String>): Long {
        fun readSegment(s: String, startPos: Int, current: BinSegment): Int {
            var pos = startPos
            var v = s.substring(pos, pos+3).toInt(2)
            pos+=3
            var t = s.substring(pos, pos+3).toInt(2)
            pos+=3
            var seg = BinSegment(v, t, current)
            current.addSegment(seg)
            if (seg.type == 4) {
                var literalString = ""
                while(s.substring(pos, pos+1) == "1") {
                    var tmpString = s.substring(pos+1, pos+5)
                    pos+=5
                    literalString+=tmpString
                }
                var tmpString = s.substring(pos+1, pos+5)
                pos+=5
                literalString+=tmpString
                seg.addLiteral(literalString)
            } else {
                var id = s.substring(pos, pos+1)
                pos+=1
                if (id == "0") {
                    // read next 15 bits for length
                    var l = s.substring(pos, pos+15).toInt(2)
                    pos+=15
                    var rightLimit = pos + l
                    while(pos < rightLimit) {
                        pos = readSegment(s, pos, seg)
                    }
                } else {
                    // read next 11 bits for segment count
                    var cnt = s.substring(pos, pos+11).toInt(2)
                    pos+=11
                    for(i in 1..cnt) {
                        pos = readSegment(s, pos, seg)
                    }
                }
            }
            return pos
        }
        fun sumVersions(root: BinSegment): Int {
            if (root.children.isEmpty()) return root.ver

            var localSum = root.ver
            for (c in root.children) {
                localSum += sumVersions(c)
            }
            return localSum
        }

        var s = ""
        for(c in input[0]) {
            s += c.toString().toInt(16).toString(2).padStart(4, '0')
        }
        var root = BinSegment(0,0, null)

        readSegment(s, 0, root)

        var value = root.children[0].calculate()
        return value
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    assert(part1(testInput) == 20)
    assert(part2(testInput) == 1L)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
