import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Exception
import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

data class Command(val c: String, val a: Char, val b: String? = null)
class ALU {
    var input = Stack<Int>()
    var regs = mutableMapOf('w' to 0, 'x' to 0, 'y' to 0, 'z' to 0)
    fun reset(){
        regs = mutableMapOf('w' to 0, 'x' to 0, 'y' to 0, 'z' to 0)
        zeroPresent = false
    }
    var zeroPresent = false
    fun setInput(n: Long) {
        var s = Stack<Int>()
        var t = n.toString()
        for(i in t.indices.reversed()) {
            s.push(t[i] - '0')
            if (t[i] == '0') zeroPresent = true
        }
        input = s
    }
    fun inp(a: Char) {
        val v = input.pop()
        //println("x="+regs['x']+", y="+regs['y']+", z="+regs['z']+" inp="+v)
        regs[a] = v
    }
    fun add(a: Char, b: Char) {
        regs[a] = regs[a]!! + regs[b]!!
    }
    fun add(a: Char, b: Int) {
        regs[a] = regs[a]!! + b
    }
    fun mul(a: Char, b: Char) {
        regs[a] = regs[a]!! * regs[b]!!
    }
    fun mul(a: Char, b: Int) {
        regs[a] = regs[a]!! * b
    }
    fun div(a: Char, b: Char) {
        regs[a] = regs[a]!! / regs[b]!!
    }
    fun div(a: Char, b: Int) {
        regs[a] = regs[a]!! / b
    }
    fun mod(a: Char, b: Char) {
        regs[a] = floorMod(regs[a]!!, regs[b]!!)
    }
    fun mod(a: Char, b: Int) {
        regs[a] = floorMod(regs[a]!!, b)
    }
    fun eql(a: Char, b: Char) {
        regs[a] = if (regs[a]!! == regs[b]!!) 1 else 0
    }
    fun eql(a: Char, b: Int) {
        regs[a] = if (regs[a]!! == b) 1 else 0
    }
    fun runCommand(c: Command) {
        when(c.c) {
            "inp" -> inp(c.a)
            "add" -> if (c.b!![0] !in 'w'..'z') add(c.a, c.b!!.toInt()) else add(c.a, c.b!![0])
            "mul" -> if (c.b!![0] !in 'w'..'z') mul(c.a, c.b!!.toInt()) else mul(c.a, c.b!![0])
            "div" -> if (c.b!![0] !in 'w'..'z') div(c.a, c.b!!.toInt()) else div(c.a, c.b!![0])
            "mod" -> if (c.b!![0] !in 'w'..'z') mod(c.a, c.b!!.toInt()) else mod(c.a, c.b!![0])
            "eql" -> if (c.b!![0] !in 'w'..'z') eql(c.a, c.b!!.toInt()) else eql(c.a, c.b!![0])
        }
    }
    fun isValid(): Boolean {
        return regs['z'] == 0
    }
 }
fun main() {
    fun part1(input: List<String>): Long {
        var commands = mutableListOf<Command>()
        for (c in input) {
            var seg = c.split(' ')
            commands.add(Command(seg[0], seg[1][0], if (seg.size > 2) seg[2] else null))
        }
        var alu = ALU()
        for(i in 73181221197111 downTo 73181221196111) {
//        for(j in 111111..999999) {
//            val i = j * 100000000L + 99999999L
            alu.reset()
            alu.setInput(i)
            if (alu.zeroPresent) continue
            for(command in commands) {
                //println(command.c + " " + command.a + " " + command.b)
                alu.runCommand(command)
            }
            if (alu.isValid()) return i
            //println("n "+i)
        }
        return 0
    }



    fun part2(input: List<String>): Int {

        return 0
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day24_test")
//    assert(part1(testInput) == 0)
//    assert(part2(testInput) == 0)

    val input = readInput("Day24")
    println(part1(input))
    println(part2(input))
}
