import java.lang.Math
import java.lang.Math.*
import java.util.*
import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>): Int {
        var m = Array<Array<Int>>(input.size) { Array<Int>(input.size) { 0 } }
        for(y in input.indices) {
            for(x in input[y].indices) {
                m[x][y] = input[y][x].toString().toInt()
            }
        }

        var dp = Array<Array<Int>>(input.size) { Array<Int>(input.size) { 10000 } }
        dp[0][0] = 0
        val compare: Comparator<Pair<Int, Pair<Int, Int>>> = compareBy { it.first }
        var q = PriorityQueue(compare)
        q.add(Pair(0, Pair(0, 0)))
        dp[0][0] = 0
        while(!q.isEmpty()) {
            var p = q.remove().second
            var x = p.first
            var y = p.second
            for((nx, ny) in listOf(Pair(x-1,y),Pair(x+1,y),Pair(x,y-1),Pair(x,y+1))) {
                if (nx >= 0 && nx < m.size && ny >= 0 && ny < m.size) {
                    if (m[nx][ny] + dp[x][y] < dp[nx][ny]) {
                        dp[nx][ny] = m[nx][ny] + dp[x][y]
                        q.add(Pair(dp[nx][ny], Pair(nx, ny)))
                    }
                }
            }
        }
        return dp[dp.size-1][dp.size-1]
    }



    fun part2(input: List<String>): Int {
        fun risk(mem: Array<Array<Int>>, m: Array<Array<Int>>, x: Int, y:Int): Int {
            if(mem[x][y] > 0) return mem[x][y]
            var tx = floorMod(x, m.size)
            var ty = floorMod(y, m.size)
            var mx = floorDiv(x, m.size)
            var my = floorDiv(y, m.size)
            var s = m[tx][ty] + mx + my
            if (s > 9) s -= 9
            mem[x][y] = s
            return s
        }

        var m = Array<Array<Int>>(input.size) { Array<Int>(input.size) { 0 } }
        var mem = Array<Array<Int>>(5*input.size) { Array<Int>(5*input.size) { 0 } }
        for(y in input.indices) {
            for(x in input[y].indices) {
                m[x][y] = input[y][x].toString().toInt()
            }
        }

        var dp = Array<Array<Int>>(5*input.size) { Array<Int>(5*input.size) { 10000 } }
        val compare: Comparator<Pair<Int, Pair<Int, Int>>> = compareBy { it.first }
        var q = PriorityQueue(compare)
        q.add(Pair(0, Pair(0, 0)))
        dp[0][0] = 0
        while(!q.isEmpty()) {
            var p = q.remove().second
            var x = p.first
            var y = p.second
            for((nx, ny) in listOf(Pair(x-1,y),Pair(x+1,y),Pair(x,y-1),Pair(x,y+1))) {
                if (nx >= 0 && nx < 5*m.size && ny >= 0 && ny < 5*m.size) {
                    if (risk(mem,m, nx,ny) + dp[x][y] < dp[nx][ny]) {
                        dp[nx][ny] = risk(mem,m, nx,ny) + dp[x][y]
                        q.add(Pair(dp[nx][ny], Pair(nx, ny)))
                    }
                }
            }
        }
        return dp[dp.size-1][dp.size-1]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    assert(part1(testInput) == 40)
    assert(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
