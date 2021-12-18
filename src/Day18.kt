import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    class Pair(var parent: Pair?,
               var isLeft: Boolean,
               var leftPair: Pair?,
               var rightPair: Pair?,
               var leftVal: Int?,
               var rightVal: Int?) {
        fun isPure(): Boolean {
            return leftVal != null && rightVal != null
        }
        fun print(): String {
            var s = "["
            if (leftVal != null) s += leftVal.toString()
            if (leftPair != null) s += leftPair?.print()
            s += ","
            if (rightVal != null) s += rightVal.toString()
            if (rightPair != null) s += rightPair?.print()
            s+="]"
            return s
        }
        fun add(arg: Pair): Pair {
            var newLeft = Pair(this, true, this.leftPair, this.rightPair, this.leftVal, this.rightVal)
            this.leftPair?.parent = newLeft
            this.rightPair?.parent = newLeft
            arg.parent = this
            this.leftPair = newLeft
            this.leftVal = null
            this.rightPair = arg
            this.rightVal = null


            return this
        }
        fun explode() {
            fun findRightmost(current: Pair, value: Int) {
                var cur = current
                while (cur.rightVal == null) {
                    cur = cur.rightPair!!
                }
                cur.rightVal = cur.rightVal!! + value
            }
            fun findLeftmost(current: Pair, value: Int) {
                var cur = current
                while (cur.leftVal == null) {
                    cur = cur.leftPair!!
                }
                cur.leftVal = cur.leftVal!! + value
            }
            fun findLeft(current: Pair, value: Int, fromLeft: Boolean) {
                if (fromLeft) {
                    if (current.parent == null) return
                    findLeft(current.parent!!, value, current.isLeft)
                } else {
                    if (current.leftVal != null) {
                        current.leftVal = current.leftVal!! + value
                        return
                    }
                    findRightmost(current.leftPair!!, value)
                }
            }
            fun findRight(current: Pair, value: Int, fromLeft: Boolean) {
                if (!fromLeft) {
                    if (current.parent == null) {
                        return
                    }
                    findRight(current.parent!!, value, current.isLeft)
                } else {
                    if (current.rightVal != null) {
                        current.rightVal = current.rightVal!! + value
                        return
                    }
                    findLeftmost(current.rightPair!!, value)
                }
            }
            var l = leftVal!!
            var r = rightVal!!
            findLeft(parent!!, l, isLeft)
            findRight(parent!!, r, isLeft)

            if (isLeft) {
                this.parent!!.leftPair = null
                this.parent!!.leftVal = 0
            } else {
                this.parent!!.rightPair = null
                this.parent!!.rightVal = 0
            }
        }
        fun split() {
            var left = leftVal?:0 >= 10
            val value = if (left) leftVal else rightVal
            var l = value!!.floorDiv(2)
            var r = value!!.floorDiv(2) + value!!.mod(2)
            if (left) {
                leftVal = null
                leftPair = Pair(this, true, null, null, l, r)
            } else {
                rightVal = null
                rightPair = Pair(this, false, null, null, l, r)
            }
        }
        fun verify() {
            if (parent != null) {
                if (isLeft && parent?.leftPair != this) {
                    println("left doesn't match")
                }
                if (!isLeft && parent?.rightPair != this) {
                    println("right doesn't match")
                }
            }
            if (leftPair!=null) leftPair?.verify()
            if (rightPair!=null) rightPair?.verify()
        }
        fun traverse(root: Pair, depth: Int): Pair? {
            if (depth == 4) return root
            var lp: Pair? = null
            if (root.leftPair != null)
                lp = traverse(root.leftPair!!, depth+1)
            if (lp == null) {
                if (root.rightPair != null) return traverse(root.rightPair!!, depth+1)
            } else {
                return lp
            }
            return null
        }
        fun traverseVal(root: Pair): Pair? {
            if (root.leftPair != null) {
                var lp = traverseVal(root.leftPair!!)
                if (lp != null) return  lp
            }
            if (root.leftVal?:0 >= 10) return root
            if (root.rightPair != null) {
                var rp = traverseVal(root.rightPair!!)
                if (rp != null) return rp
            }
            if (root.rightVal?:0 >= 10) return root

            return null
        }
        fun reduce(): Unit {
            var noop = false
            var step = 0
            while(!noop) {
                step++
                noop = true
                var pairToExplode = traverse(this, 0)
                if (pairToExplode != null) {
                    pairToExplode.explode()
                    print(" expl ")
                    noop = false
                } else {
                    var pairToSplit = traverseVal(this)
                    if (pairToSplit != null) {
                        pairToSplit.split()
                        print(" split ")
                        noop = false
                    }
                }
                if (!noop) println("step " + step + ": " + this.print())
            }
        }
        fun magnitude(): Int {
            var mag = 3 * (if (leftVal != null) (leftVal!!) else leftPair?.magnitude()?:0)
            mag += 2 * (if (rightVal != null) (rightVal!!) else rightPair?.magnitude()?:0)
            return mag
        }
    }
    fun part1(input: List<String>): Int {
        fun parseInput(st: String, root: Pair?, isLeft: Boolean): Pair {
            var s = st.substring(1, st.length-1)
            var leftVal: Int? = null
            if (s[0] != '[') {
                leftVal = s.substringBefore(',').toInt()
            }
            var rightVal: Int? = null
            if (s[s.length-1] != ']') {
                rightVal = s.substring(s.length-1, s.length).toInt()
            }
            var count = 0
            var midPoint = 0
            for(i in s.indices) {
                if (s[i] == '[') {
                    count++
                } else if (s[i] == ']') {
                    count--
                } else if (s[i] == ',') {
                    if (count == 0) {
                        midPoint = i
                        break
                    }
                }
            }
            if (leftVal != null && rightVal != null) {
                return Pair(root, isLeft, null, null, leftVal, rightVal)
            } else {
                if (leftVal != null) {
                    var newPair = Pair(root, isLeft, null, null, leftVal, null)
                    newPair.rightPair = parseInput(s.substringAfter(','), newPair, false)
                    return newPair
                }
                if (rightVal != null) {
                    var newPair = Pair(root, isLeft, null, null, null, rightVal)
                    newPair.leftPair = parseInput(s.substring(0, midPoint), newPair, true)
                    return newPair
                }
                var newPair = Pair(root, isLeft, null, null, null, null)
                newPair.leftPair = parseInput(s.substring(0, midPoint), newPair, true)
                newPair.rightPair = parseInput(s.substring(midPoint+1, s.length), newPair, false)
                return newPair
            }
        }
        var root = parseInput(input[0], null, true)
        println(root.print())
        for(i in 1 until input.size) {
            var pair = parseInput(input[i], root, false)
            println(pair.print())
            root.verify()
            root = root.add(pair)
            root.verify()
            println("add: "+root.print())
            root.reduce()
            root.verify()
            println("reduce: " + root.print())
        }

        var mag = root.magnitude()
        return mag
    }



    fun part2(input: List<String>): Int {
        fun parseInput(st: String, root: Pair?, isLeft: Boolean): Pair {
            var s = st.substring(1, st.length-1)
            var leftVal: Int? = null
            if (s[0] != '[') {
                leftVal = s.substringBefore(',').toInt()
            }
            var rightVal: Int? = null
            if (s[s.length-1] != ']') {
                rightVal = s.substring(s.length-1, s.length).toInt()
            }
            var count = 0
            var midPoint = 0
            for(i in s.indices) {
                if (s[i] == '[') {
                    count++
                } else if (s[i] == ']') {
                    count--
                } else if (s[i] == ',') {
                    if (count == 0) {
                        midPoint = i
                        break
                    }
                }
            }
            if (leftVal != null && rightVal != null) {
                return Pair(root, isLeft, null, null, leftVal, rightVal)
            } else {
                if (leftVal != null) {
                    var newPair = Pair(root, isLeft, null, null, leftVal, null)
                    newPair.rightPair = parseInput(s.substringAfter(','), newPair, false)
                    return newPair
                }
                if (rightVal != null) {
                    var newPair = Pair(root, isLeft, null, null, null, rightVal)
                    newPair.leftPair = parseInput(s.substring(0, midPoint), newPair, true)
                    return newPair
                }
                var newPair = Pair(root, isLeft, null, null, null, null)
                newPair.leftPair = parseInput(s.substring(0, midPoint), newPair, true)
                newPair.rightPair = parseInput(s.substring(midPoint+1, s.length), newPair, false)
                return newPair
            }
        }

        var result = 0
        for(i in input.indices) {
            for(j in input.indices) {
                if (i != j) {
                    var root = parseInput(input[i], null, true)
                    var pair = parseInput(input[j], root, false)
                    println(pair.print())
                    root.verify()
                    root = root.add(pair)
                    root.verify()
                    println("add: "+root.print())
                    root.reduce()
                    root.verify()
                    println("reduce: " + root.print())
                    var m = root.magnitude()
                    if (m > result) result = m
                }
            }


        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    assert(part1(testInput) == 4140)
    assert(part2(testInput) == 3993)

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
