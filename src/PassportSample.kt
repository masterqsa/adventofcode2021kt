class PassportSample(private val map: Map<String, String>) {
    companion object {
        fun fromString(s: String): PassportSample {
            val fieldsAndValues = s.split(" ", "\n", "\r\n")
            val map = fieldsAndValues.associate {
                val (key, value) = it.split(":")
                key to value
            }
            return PassportSample(map)
        }
    }
    private val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" /*"cid"*/)

    fun hasAllRequiredFields() = map.keys.containsAll(requiredFields)
    fun hasValidValues(): Boolean =
        map.all { (key, value) ->
            when (key) {
                "byr" -> value.length == 4 && value.toIntOrNull() in 1920..2002
                "iyr" -> value.length == 4 && value.toIntOrNull() in 2010..2020
                "eyr" -> value.length == 4 && value.toIntOrNull() in 2020..2030
                "pid" -> value.length == 9 && value.all(Char::isDigit)
                "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "hgt" -> when (value.takeLast(2)) {
                    "cm" -> value.removeSuffix("cm").toIntOrNull() in 150..193
                    "in" -> value.removeSuffix("in").toIntOrNull() in 59..76
                    else -> false
                }
                "hcl" -> value matches """#[0-9a-f]{6}""".toRegex()
                else -> true
            }
        }
}