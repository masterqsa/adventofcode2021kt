import java.lang.Math

fun main() {
    fun part1(input: List<String>): Int {
        var days = 80-1
        var fish = input[0].split(',').map(String::toInt)
        for(day in 0..days) {
            var newFish = mutableListOf<Int>()
            for(f in fish) {
                if (f > 0) {
                    newFish.add(f-1)
                } else {
                    newFish.add(6)
                    newFish.add(8)
                }
            }
            fish = newFish
        }

        return fish.size
    }

    fun part2(input: List<String>): Long {

        var days = 256-1
        var fish = input[0].split(',').map(String::toInt)
        var ages = Array<Long>(9) { 0 }
        for(f in fish) {
            ages[f]++
        }
        for(day in 0..days) {
            var a6 = 0L
            var a8 = 0L
            for (a in 0..8) {
                if (a > 0) {
                    ages[a-1]=ages[a]
                    ages[a] = 0
                } else {
                    a6=ages[0]
                    a8=ages[0]
                    ages[0]=0
                }
            }
            ages[6]+=a6
            ages[8]+=a8
        }
        var x = ages.sum()
        //print(x)
        return x
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    assert(part1(testInput) == 5934)
    assert(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
