import java.lang.Math
import java.lang.Math.*
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        fun navigate(m: Array<Array<Int>>, dp: Array<Array<Int>>, x: Int, y: Int): Unit {
            if (x > 0) {
                if (m[x-1][y] + dp[x][y] < dp[x-1][y]) {
                    dp[x - 1][y] = m[x - 1][y] + dp[x][y]
                    navigate(m, dp, x-1, y)
                }
            }
            if (x < m.size-1) {
                if (m[x+1][y] + dp[x][y] < dp[x+1][y]) {
                    dp[x + 1][y] = m[x + 1][y] + dp[x][y]
                    navigate(m, dp, x+1, y)
                }
            }
            if (y > 0) {
                if (m[x][y-1] + dp[x][y] < dp[x][y-1]) {
                    dp[x][y-1] = m[x][y-1] + dp[x][y]
                    navigate(m, dp, x, y-1)
                }
            }
            if (y < m.size-1) {
                if (m[x][y+1] + dp[x][y] < dp[x][y+1]) {
                    dp[x][y+1] = m[x][y+1] + dp[x][y]
                    navigate(m, dp, x, y+1)
                }
            }
        }
        var m = Array<Array<Int>>(input.size) { Array<Int>(input.size) { 0 } }
        for(y in input.indices) {
            for(x in input[y].indices) {
                m[x][y] = input[y][x].toString().toInt()
            }
        }

        var dp = Array<Array<Int>>(input.size) { Array<Int>(input.size) { 10000 } }
        dp[0][0] = 0
        navigate(m, dp, 0, 0)
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
        fun navigate(mem: Array<Array<Int>>,m: Array<Array<Int>>, dp: Array<Array<Int>>, x: Int, y: Int): Unit {
            if (x > 0) {
                if (risk(mem,m, x-1,y) + dp[x][y] < dp[x-1][y]) {
                    dp[x - 1][y] = risk(mem,m, x - 1,y) + dp[x][y]
                    navigate(mem,m, dp, x-1, y)
                }
            }
            if (x < 5*m.size-1) {
                if (risk(mem,m,x+1,y) + dp[x][y] < dp[x+1][y]) {
                    dp[x + 1][y] = risk(mem,m, x + 1,y) + dp[x][y]
                    navigate(mem,m, dp, x+1, y)
                }
            }
            if (y > 0) {
                if (risk(mem,m,x,y-1) + dp[x][y] < dp[x][y-1]) {
                    dp[x][y-1] = risk(mem,m,x,y-1) + dp[x][y]
                    navigate(mem,m, dp, x, y-1)
                }
            }
            if (y < 5*m.size-1) {
                if (risk(mem,m,x,y+1) + dp[x][y] < dp[x][y+1]) {
                    dp[x][y+1] = risk(mem,m,x,y+1) + dp[x][y]
                    navigate(mem,m, dp, x, y+1)
                }
            }
        }
        var m = Array<Array<Int>>(input.size) { Array<Int>(input.size) { 0 } }
        var mem = Array<Array<Int>>(5*input.size) { Array<Int>(5*input.size) { 0 } }
        for(y in input.indices) {
            for(x in input[y].indices) {
                m[x][y] = input[y][x].toString().toInt()
            }
        }

        var dp = Array<Array<Int>>(5*input.size) { Array<Int>(5*input.size) { 10000 } }
        dp[0][0] = 0
        navigate(mem,m, dp, 0, 0)
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
