import java.lang.Math.*

data class GameState(val p1: Int, val p2: Int, val scoreP1: Int, val scoreP2: Int)

fun main() {
    fun part1(input: List<String>): Int {
        fun incrementDie(c: Int): Int {
            if (c < 100) return c+1
            return 1
        }

        var p1 = input[0].substringAfter(": ").toInt()
        var p2 = input[1].substringAfter(": ").toInt()

        val limit = 1000
        var turns = 0
        var p1sum = 0
        var p2sum = 0
        var currentDie = 1
        while(p2sum < 1000) {
            var t = currentDie
            currentDie = incrementDie(currentDie)
            t+=currentDie
            currentDie = incrementDie(currentDie)
            t+=currentDie
            currentDie = incrementDie(currentDie)
            p1 += floorMod(t, 10)
            if (p1 > 10) p1 -= 10
            turns+=3
            p1sum+=p1
            if (p1sum >= limit) break
            t = currentDie
            currentDie = incrementDie(currentDie)
            t+=currentDie
            currentDie = incrementDie(currentDie)
            t+=currentDie
            currentDie = incrementDie(currentDie)
            p2 += floorMod(t,10)
            if (p2 > 10) p2 -= 10
            turns+=3
            p2sum+=p2
            if (p2sum >= limit) break
        }
        return turns * min(p1sum, p2sum)
    }



    fun part2(input: List<String>): Long {
        var p1 = input[0].substringAfter(": ").toInt()
        var p2 = input[1].substringAfter(": ").toInt()

        var limit = 21

        var p1wins = 0L
        var p2wins = 0L
        var states = mutableMapOf(GameState(p1, p2, 0, 0) to 1L)

        while (states.isNotEmpty()) {
            var state = states.keys.first()
            var l = states[state]!!

            val (p1, p2, scoreP1, scoreP2) = state

            for(d1 in 1..3) {
                for(d2 in 1..3) {
                    for(d3 in 1..3) {
                        var px1 = p1
                        px1 += d1 + d2 + d3
                        if (px1 > 10) px1 -= 10
                        var scorePx1 = scoreP1

                        scorePx1 += px1
                        if(scorePx1 >= limit) {
                            p1wins+= l
                        } else {
                            for(dx1 in 1..3) {
                                for(dx2 in 1..3) {
                                    for(dx3 in 1..3) {
                                        var px2 = p2
                                        px2 += dx1 + dx2 + dx3
                                        if (px2 > 10) px2 -= 10

                                        var scorePx2 = scoreP2
                                        scorePx2 += px2
                                        if(scorePx2 >= limit) {
                                            p2wins+= l
                                        } else {
                                            val state1 = GameState(px1, px2, scorePx1, scorePx2)
                                            if(state1 in states){
                                                states[state1] = l + states[state1]!!
                                            } else {
                                                states[state1] = l
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            states.remove(state)
        }

        return max(p1wins, p2wins)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21_test")
    assert(part1(testInput) == 739785)
    assert(part2(testInput) == 444356092776315)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}
