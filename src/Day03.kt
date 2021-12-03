fun main() {
    fun part1(input: List<String>): Int {
        val arr = Array(input[0].length) { i -> 0 }
        for(j in input[0].indices) {
            arr[j] = 0
        }
        for(i in input.indices) {
            for(j in input[i].indices) {
                if (input[i][j] == '1') {
                    arr[j]++
                }
            }
        }
        var gamma = 0
        var epsilon = 0
        var count = input.size
        for(i in arr.indices) {
            gamma *= 2
            epsilon *= 2
            if (arr[i] >= count/2) {
                gamma+=1
            } else {
                epsilon+=1
            }
        }
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val arr = Array(input[0].length) { i -> 0 }
        
        var inputs = input
        var gamma = 0
        var epsilon = 0
        var oxygen = ""
        var co2 = ""
        for(i in input[0].indices) {
            arr[i] = 0
            for(j in inputs.indices) {
                if (inputs[j][i] == '1') {
                    arr[i]++
                }
            }
            var prevalent = '1';
            if (arr[i]*2 < inputs.size) {
                prevalent = '0'
            }
            inputs = inputs.filter { s -> s[i] == prevalent }

            if (inputs.size == 1) {
                oxygen = inputs[0]
                break
            }
        }
        inputs = input
        for(i in input[0].indices) {
            arr[i] = 0
            for(j in inputs.indices) {
                if (inputs[j][i] == '1') {
                    arr[i]++
                }
            }
            var prevalent = '1';
            if (arr[i]*2 < inputs.size) {
                prevalent = '0'
            }
            inputs = inputs.filter { s -> s[i] != prevalent }

            if (inputs.size == 1) {
                co2 = inputs[0]
                break
            }
        }
        gamma = oxygen.toInt(2)
        epsilon = co2.toInt(2)

        return gamma * epsilon
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    assert(part1(testInput) == 198)
    assert(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
