import com.sun.org.apache.xpath.internal.operations.Bool
import java.lang.Exception
import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

class Register(var position: Int, var depth: Int = 4) {
    var validChars: Set<Char> = setOf()
    var targetChar: Char = 'E'
    init {
        when(position) {
            0 -> { validChars = setOf('E', 'A')
                targetChar = 'A'}
            1 -> { validChars = setOf('E', 'B')
                targetChar = 'B'}
            2 -> { validChars = setOf('E', 'C')
                targetChar = 'C'}
            3 -> { validChars = setOf('E', 'D')
                targetChar = 'D'}
        }
    }
    var stack: Array<Char> = Array<Char>(depth) { 'E' }
    fun clone(): Register {
        var c = Register(this.position)
        c.stack = this.stack.copyOf()
        return c
    }
    fun nextChar(): Char {
        for(i in stack.indices) {
            if (stack[i] != 'E') {
                return stack[i]
            }
        }
        return 'E'
    }
    fun nextPos(): Int {
        for(i in stack.indices) {
            if (stack[i] != 'E') {
                return i
            }
        }
        return -1
    }
    fun put(c: Char): Int {
        for(i in stack.indices.reversed()) {
            if (stack[i] == 'E') {
                stack[i] = c
                return i+1
            }
        }
        return 0
    }
    fun pull(): Int {
        for(i in stack.indices) {
            if (stack[i] != 'E') {
                stack[i] = 'E'
                return i+1
            }
        }
        return 0
    }
    fun isEmpty(): Boolean {
        return stack[stack.size-1] == 'E'
    }
    fun isReady(): Boolean {
        if (stack[0] != 'E') return  false
        for(i in stack.indices) {
            if (stack[i] !in validChars) {
                return false
            }
        }
        return true
    }
    fun isComplete(): Boolean {
        for(i in stack.indices) {
            if (stack[i] != targetChar) {
                return false
            }
        }
        return true
    }
}
class Field(var registers: Array<Register>, var hall: Array<Char>, var score: Int) {
    var targetMap = mapOf(0 to 'A', 1 to 'B',2 to 'C',3 to 'D' )
    var targetIndex = mapOf('A' to 0, 'B' to 1,'C' to 2,'D' to 3 )
    fun clone(): Field {
        var newRegisters = arrayOf(registers[0].clone(), registers[1].clone(), registers[2].clone(), registers[3].clone())
        var newHall = hall.copyOf()
        var f = Field(newRegisters, newHall, score)
        return f
    }
    fun signature(): String {
        var s = hall.toString()
        for(i in registers.indices) {
            s += registers[i].stack.toString()
        }
        return s
    }
    fun isComplete(): Boolean {
        return registers.all { it.isComplete() }
    }
    fun unblockedPath(hallIdx: Int, regIdx: Int): Boolean {
        var blockingIdxs = mutableSetOf<Int>()
        if(hallIdx in 0..1) {
            if (hallIdx == 0) blockingIdxs.add(1)
            if (regIdx == 0) return areHallsFree(blockingIdxs)
            blockingIdxs.add(2)
            if (regIdx == 1) return areHallsFree(blockingIdxs)
            blockingIdxs.add(3)
            if (regIdx == 2) return areHallsFree(blockingIdxs)
            blockingIdxs.add(4)
            if (regIdx == 3) return areHallsFree(blockingIdxs)
        } else if (hallIdx == 2) {
            if (regIdx in 0..1) return true
            blockingIdxs.add(3)
            if (regIdx == 2) return areHallsFree(blockingIdxs)
            blockingIdxs.add(4)
            if (regIdx == 3) return areHallsFree(blockingIdxs)
        } else if (hallIdx == 3) {
            if (regIdx in 1..2) return true
            if (regIdx == 0) {
                blockingIdxs.add(2)
                return areHallsFree(blockingIdxs)
            }
            if (regIdx == 3) {
                blockingIdxs.add(4)
                return areHallsFree(blockingIdxs)
            }
        } else if (hallIdx == 4) {
            if (regIdx in 2..3) return true
            blockingIdxs.add(3)
            if (regIdx == 1) return areHallsFree(blockingIdxs)
            blockingIdxs.add(2)
            if (regIdx == 0) return areHallsFree(blockingIdxs)
        } else if(hallIdx in 5..6) {
            if (hallIdx == 6) blockingIdxs.add(5)
            if (regIdx == 3) return areHallsFree(blockingIdxs)
            blockingIdxs.add(4)
            if (regIdx == 2) return areHallsFree(blockingIdxs)
            blockingIdxs.add(3)
            if (regIdx == 1) return areHallsFree(blockingIdxs)
            blockingIdxs.add(2)
            if (regIdx == 0) return areHallsFree(blockingIdxs)
        }
        return true
    }

    fun areHallsFree(idxs: Set<Int>): Boolean {
        for(i in idxs) {
            if (hall[i] != 'E') return false
        }
        return true
    }
    fun distance(hallIdx: Int, regIdx: Int): Int {
        when {
            hallIdx == 0 && regIdx == 0 -> return 2
            hallIdx == 1 && regIdx == 0 -> return 1
            hallIdx == 2 && regIdx == 0 -> return 1
            hallIdx == 3 && regIdx == 0 -> return 3
            hallIdx == 4 && regIdx == 0 -> return 5
            hallIdx == 5 && regIdx == 0 -> return 7
            hallIdx == 6 && regIdx == 0 -> return 8
            hallIdx == 0 && regIdx == 1 -> return 4
            hallIdx == 1 && regIdx == 1 -> return 3
            hallIdx == 2 && regIdx == 1 -> return 1
            hallIdx == 3 && regIdx == 1 -> return 1
            hallIdx == 4 && regIdx == 1 -> return 3
            hallIdx == 5 && regIdx == 1 -> return 5
            hallIdx == 6 && regIdx == 1 -> return 6
            hallIdx == 0 && regIdx == 2 -> return 6
            hallIdx == 1 && regIdx == 2 -> return 5
            hallIdx == 2 && regIdx == 2 -> return 3
            hallIdx == 3 && regIdx == 2 -> return 1
            hallIdx == 4 && regIdx == 2 -> return 1
            hallIdx == 5 && regIdx == 2 -> return 3
            hallIdx == 6 && regIdx == 2 -> return 4
            hallIdx == 0 && regIdx == 3 -> return 8
            hallIdx == 1 && regIdx == 3 -> return 7
            hallIdx == 2 && regIdx == 3 -> return 5
            hallIdx == 3 && regIdx == 3 -> return 3
            hallIdx == 4 && regIdx == 3 -> return 1
            hallIdx == 5 && regIdx == 3 -> return 1
            hallIdx == 6 && regIdx == 3 -> return 2
        }
        throw Exception("Wrong param for distance")
    }
    fun costMultiple(c: Char): Int {
        when(c) {
            'A' -> return 1
            'B' -> return 10
            'C' -> return 100
            'D' -> return 1000
        }
        throw Exception("Wrong param for cost multiple")
    }
    fun tryReturn(): Boolean {
        for(i in hall.indices) {
            if (hall[i] != 'E' && registers[targetIndex[hall[i]]!!].isReady()) {
                if (unblockedPath(i, targetIndex[hall[i]]!!)) {
                    var costMult = costMultiple(hall[i])
                    var dist = distance(i, targetIndex[hall[i]]!!)
                    dist+= registers[targetIndex[hall[i]]!!].put(hall[i])
                    hall[i] = 'E'
                    score+=(costMult * dist)
                    return true
                }
            }
        }
        for(i in registers.indices) {
            for(j in registers.indices) {
                if (i != j) {
                    if (!registers[i].isEmpty() && !registers[i].isReady() && !registers[i].isComplete() && registers[j].isReady()) {
                        var c = registers[i].nextChar()
                        if (targetIndex[c]!! == j) {
                            // see if there is a path to return home, use hall indexes adjacent to source register
                            var pathExists = false
                            if (i < j) {
                                pathExists = unblockedPath(i + 1, j)
                            } else {
                                pathExists = unblockedPath(i + 2, j)
                            }
                            if (pathExists) {
                                var distance = if (i < j) 2*(j-i) else 2*(i-j)
                                var costMult = costMultiple(c)
                                distance+= registers[i].pull()
                                distance+= registers[j].put(c)
                                score+= (costMult * distance)
                                return  true
                            }
                        }
                    }
                }
            }
        }
        return false
    }
    fun moveOptions(): List<Pair<Int, Int>> {
        var options = mutableListOf<Pair<Int, Int>>()
        for(i in registers.indices) {
            if (!registers[i].isComplete() && !registers[i].isReady() && !registers[i].isEmpty()) {
                for(j in hall.indices) {
                    if (hall[j] == 'E' && unblockedPath(j, i)) {
                        options.add(Pair(i, j))
                    }
                }
            }
        }
        return options
    }
}
fun main() {
    fun part1(input: List<String>): Int {
        var register0 = Register(0, 2)
        register0.stack[0] = input[2][3]
        register0.stack[1] = input[3][3]
        var register1 = Register(1, 2)
        register1.stack[0] = input[2][5]
        register1.stack[1] = input[3][5]
        var register2 = Register(2, 2)
        register2.stack[0] = input[2][7]
        register2.stack[1] = input[3][7]
        var register3 = Register(3, 2)
        register3.stack[0] = input[2][9]
        register3.stack[1] = input[3][9]
        var startRegisters = arrayOf( register0, register1, register2, register3)
        var startHall = Array<Char>(7) { 'E' }

        var memo = mutableMapOf<String, Int>()
        var bestScore = Int.MAX_VALUE
        val compare: Comparator<Field> = compareBy { it.score }
        var q = PriorityQueue(compare)
        var startField = Field(startRegisters, startHall, 0)
        memo[startField.signature()] = 0
        q.add(startField)
        while(!q.isEmpty()) {
            var field = q.remove()
            if (field.score > bestScore)
                return bestScore
            println("Queue size: " + q.size + ", field score: " + field.score + " best score: " + bestScore)
            while(true) {
                var actioned = field.tryReturn()
                if (!actioned) break
                if (field.isComplete()) {
                    if (bestScore > field.score) {
                        bestScore = field.score
                        println("Best score: " + bestScore)
                    }
                }
            }

            var options = field.moveOptions()
            for(option in options) {
                var newField = field.clone()
                var regIdx = option.first
                var hallIdx = option.second
                var distance = newField.distance(hallIdx, regIdx)
                var c = newField.registers[regIdx].nextChar()
                var costMult = newField.costMultiple(c)
                distance+=newField.registers[regIdx].pull()
                newField.hall[hallIdx] = c
                newField.score+=(costMult * distance)
                var s = newField.signature()
                if (memo.containsKey(s)) {
                    if (newField.score < memo[s]!!) {
                        memo[s] = newField.score
                        q.add(newField)
                    } else {
                        continue
                    }
                } else {
                    q.add(newField)
                }
            }
        }

        //14510
        return 0
    }



    fun part2(input: List<String>): Int {
        var register0 = Register(0)
        register0.stack[0] = input[2][3]
        register0.stack[1] = 'D'
        register0.stack[2] = 'D'
        register0.stack[3] = input[3][3]
        var register1 = Register(1)
        register1.stack[0] = input[2][5]
        register1.stack[1] = 'C'
        register1.stack[2] = 'B'
        register1.stack[3] = input[3][5]
        var register2 = Register(2)
        register2.stack[0] = input[2][7]
        register2.stack[1] = 'B'
        register2.stack[2] = 'A'
        register2.stack[3] = input[3][7]
        var register3 = Register(3)
        register3.stack[0] = input[2][9]
        register3.stack[1] = 'A'
        register3.stack[2] = 'C'
        register3.stack[3] = input[3][9]
        var startRegisters = arrayOf( register0, register1, register2, register3)
        var startHall = Array<Char>(7) { 'E' }

        var memo = mutableMapOf<String, Int>()
        var bestScore = Int.MAX_VALUE
        val compare: Comparator<Field> = compareBy { it.score }
        var q = PriorityQueue(compare)
        var startField = Field(startRegisters, startHall, 0)
        memo[startField.signature()] = 0
        q.add(startField)
        while(!q.isEmpty()) {
            var field = q.remove()
            if (field.score > bestScore)
                return bestScore
            println("Queue size: " + q.size + ", field score: " + field.score + " best score: " + bestScore)
            while(true) {
                var actioned = field.tryReturn()
                if (!actioned) break
                if (field.isComplete()) {
                    if (bestScore > field.score) {
                        bestScore = field.score
                        println(bestScore)
                    }
                }
            }
            var options = field.moveOptions()
            for(option in options) {
                var newField = field.clone()
                var regIdx = option.first
                var hallIdx = option.second
                var distance = newField.distance(hallIdx, regIdx)
                var c = newField.registers[regIdx].nextChar()
                var costMult = newField.costMultiple(c)
                distance+=newField.registers[regIdx].pull()
                newField.hall[hallIdx] = c
                newField.score+=(costMult * distance)
                var s = newField.signature()
                if (memo.containsKey(s)) {
                    if (newField.score < memo[s]!!) {
                        memo[s] = newField.score
                        q.add(newField)
                    } else {
                        continue
                    }
                } else {
                    q.add(newField)
                }
            }
        }
        return bestScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day23_test")
    assert(part1(testInput) == 12521)
    assert(part2(testInput) == 44169)

    val input = readInput("Day23")
    println(part1(input))
    println(part2(input))
}
