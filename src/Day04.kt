class MatrixReference(var n: Int, var row: Int, var col: Int) {
}

fun main() {
    fun part1(input: List<String>): Int {
        val sequence = input[0].split(',').map(String::toInt)

        var matrices = mutableListOf<Array<Array<Int>>>()
        var hits = mutableListOf<Array<Array<Boolean>>>()
        var mapping = mutableMapOf<Int, MutableSet<MatrixReference>>()
        var count = -1
        var row = 0
        for(i in 1 until input.size) {
            if (input[i] == "") {
                matrices.add(Array(5) {Array(5) {0} })
                hits.add(Array(5) { Array (5) { false } })
                count++
                row = 0
            } else {
                var line = input[i].trim().split("   ", "  ", " ").map(String::toInt)
                for(col in line.indices) {
                    matrices[count][row][col] = line[col]
                    if (mapping.containsKey(line[col])) {
                        mapping[line[col]]?.add(MatrixReference(count, row, col))
                    } else {
                        mapping[line[col]] = mutableSetOf(MatrixReference(count, row, col))
                    }
                }
                row++
            }
        }
        var sum = 0
        var calledNumber = 0
        for(i in sequence.indices) {
            var targets = mapping[sequence[i]]
            if (targets != null) {
                for(t in targets) {
                    hits[t.n][t.row][t.col] = true
                    var rowComplete = true
                    for (x in 0..4) {
                        if (!hits[t.n][t.row][x]) {
                            rowComplete = false
                        }
                    }
                    var colComplete = true
                    for (x in 0..4) {
                        if (!hits[t.n][x][t.col]) {
                            colComplete = false
                        }
                    }
                    if (rowComplete || colComplete) {
                        for (x in 0..4)
                            for (y in 0..4) {
                                if (!hits[t.n][x][y])
                                    sum += matrices[t.n][x][y]
                            }
                        calledNumber = sequence[i]
                        return sum * calledNumber
                    }
                }
            }
        }

        return sum * calledNumber
    }

    fun part2(input: List<String>): Int {
        val sequence = input[0].split(',').map(String::toInt)

        var matrices = mutableListOf<Array<Array<Int>>>()
        var hits = mutableListOf<Array<Array<Boolean>>>()
        var mapping = mutableMapOf<Int, MutableSet<MatrixReference>>()
        var count = -1
        var row = 0
        for(i in 1 until input.size) {
            if (input[i] == "") {
                matrices.add(Array(5) {Array(5) {0} })
                hits.add(Array(5) { Array (5) { false } })
                count++
                row = 0
            } else {
                var line = input[i].trim().split("   ", "  ", " ").map(String::toInt)
                for(col in line.indices) {
                    matrices[count][row][col] = line[col]
                    if (mapping.containsKey(line[col])) {
                        mapping[line[col]]?.add(MatrixReference(count, row, col))
                    } else {
                        mapping[line[col]] = mutableSetOf(MatrixReference(count, row, col))
                    }
                }
                row++
            }
        }
        var boardsWon = mutableSetOf<Int>()
        var sum = 0
        var calledNumber = 0
        for(i in sequence.indices) {
            var targets = mapping[sequence[i]]
            if (targets != null) {
                for(t in targets) {
                    if (!boardsWon.contains(t.n)) {
                        hits[t.n][t.row][t.col] = true
                        var rowComplete = true
                        for (x in 0..4) {
                            if (!hits[t.n][t.row][x]) {
                                rowComplete = false
                            }
                        }
                        var colComplete = true
                        for (x in 0..4) {
                            if (!hits[t.n][x][t.col]) {
                                colComplete = false
                            }
                        }
                        if (rowComplete || colComplete) {
                            if (boardsWon.count() == matrices.size - 1) {
                                var lastBoard = 0
                                for (b in 0..matrices.size) {
                                    if (!boardsWon.contains(b)) {
                                        lastBoard = b
                                        break
                                    }
                                }
                                for (x in 0..4)
                                    for (y in 0..4) {
                                        if (!hits[lastBoard][x][y])
                                            sum += matrices[lastBoard][x][y]
                                    }
                                calledNumber = sequence[i]
                                return sum * calledNumber
                            }
                            boardsWon.add(t.n)
                        }
                    }

                }

            }
        }

        return sum * calledNumber
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    assert(part1(testInput) == 4512)
    assert(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
