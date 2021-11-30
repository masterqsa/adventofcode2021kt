typealias Color = String
typealias Rule = Set<String>
class Samples {
    data class PasswordWithPolicy(
        val password: String,
        val range: IntRange,
        val letter: Char
        ) {
        companion object {
            fun parse(line: String) = PasswordWithPolicy(
                password = line.substringAfter(": "),
                letter = line.substringAfter(" ").substringBefore(":").single(),
                range = line.substringBefore(" ").let {
                    val (start, end) = it.split("-")
                    start.toInt()..end.toInt()
                },
            )
            private val regex = Regex("""(\d+)-(\d+) ([a-z]): ([a-z]+)""")
            fun parseUsingRegex(line: String): PasswordWithPolicy =
                regex.matchEntire(line)!!
                    .destructured
                    .let { (start, end, letter, password) ->
                        PasswordWithPolicy(password, start.toInt()..end.toInt(), letter.single())
                    }
        }
    }

    private fun countTrees(field: List<String>, vector: Pair<Int, Int>): Int {
        val (dx, dy) = vector
        val width = field[0].length
        return field.indices.count { y ->
            y % dy == 0 && field[y][y * dx / dy % width] == '#'
        }
    }

    private val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" /*"cid"*/)
    fun hasAllRequiredFields(text: String): Boolean {
        val fieldsWithValues = text.split(" ", "\n", "\r\n")
        val fieldNames = fieldsWithValues.map { it.substringBefore(":") }
        return fieldNames.containsAll(requiredFields)
    }

    fun String.toSeatID(): Int = this
        .replace('B', '1').replace('F', '0')
        .replace('R', '1').replace('L', '0')
        .toInt(radix = 2)

    val newLine = System.lineSeparator()
    //val firstAnswer = transformAndReduce(groups, Set<Char>::union)
    //val secondAnswer = transformAndReduce(groups, Set<Char>::intersect)
    private fun transformAndReduce(groups: List<String>, operation: (Set<Char>, Set<Char>) -> Set<Char>) =
        groups.map { lines ->
            lines.split(newLine).map(String::toSet)
        }.sumOf { characters ->
            characters.reduce { a, b -> operation(a , b) }.count()
        }


    private fun buildBagTree(lines: List<String>): Map<Color, Rule> {
        val rules = hashMapOf<Color, Rule>()
        lines.forEach { line ->
                val (parent, allChildren) = line
                    .replace(Regex("\\d+"), "")
                    .replace(Regex("bags?\\.?"), "")
                    .split("contain")
                    .map { it.trim() }
                val childrenColors = allChildren.split(',').map { it.trim() }.toSet()
                for (childColor in childrenColors) {
                    rules.compute(childColor) { _, current ->
                        if (current == null) setOf(parent)
                        else current + parent
                    }
                }
            }
        return rules
    }
}