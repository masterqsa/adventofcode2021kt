import java.lang.Math.*
data class Coord(val x: Int, val y: Int, val z: Int)
enum class CubeType {
    Light,
    Dark,
    NegateLight,
    NegateDark,
    Ignore
}
data class Cube(val x_range: IntRange, val y_range: IntRange, val z_range: IntRange, val type: CubeType)
class MapReactor(var map: MutableMap<Coord, Boolean>) {
    fun set(x: Int, y: Int, z: Int, v: Boolean) {
        map[Coord(x,y,z)] = v
    }
    fun get(x: Int, y: Int, z: Int): Boolean {
        val key = Coord(x,y,z)
        if (map.containsKey(key)) return map[key]!!
        return false
    }
}
fun main() {
    fun part1(input: List<String>): Int {
//on x=10..12,y=10..12,z=10..12
        var cube = mutableMapOf<Coord, Boolean>()
        var map = MapReactor(cube)
        for(i in input.indices) {
            val state = input[i].substringBefore(' ') == "on"
            val (x_range, y_range, z_range) = input[i].substringAfter(' ').split(',')
                .map { it.replace("x=","")
                    .replace("y=","")
                    .replace("z=","")}
                .map { IntRange(max(-50,it.substringBefore("..").toInt()),min(50,it.substringAfter("..").toInt()) )}
            for(x in x_range){
                for(y in y_range) {
                    for(z in z_range) {
                        if (x in -50..50 && y in -50..50 && z in -50..50)
                            map.set(x,y,z,state)
                    }
                }
            }
        }
        val r = -50..50
        var count = 0
        for(x in r){
            for(y in r) {
                for(z in r) {
                    if (map.get(x,y,z)) count++
                }
            }
        }
        return count
    }



    fun part2(input: List<String>): Long {
        fun intersect(range1: IntRange, range2: IntRange): IntRange {
            var r1 = if (range1.start <= range2.start) range1 else range2
            var r2 = if (range1.start <= range2.start) range2 else range1
            if (r1.endInclusive < r2.start) {
                return 0..-1
            } else if (r1.endInclusive <= r2.endInclusive) {
                return r2.start..r1.endInclusive
            }
            return r2
        }
        fun intersection(c1: Cube, c2: Cube): Cube? {
            var x_range = intersect(c1.x_range, c2.x_range)
            var y_range = intersect(c1.y_range, c2.y_range)
            var z_range = intersect(c1.z_range, c2.z_range)

            if (x_range.isEmpty() || y_range.isEmpty() || z_range.isEmpty()){
                return null
            }
            var type = when {
                c1.type == CubeType.Light && c2.type == CubeType.Dark -> CubeType.Dark
                c1.type == CubeType.Light && c2.type == CubeType.Light -> CubeType.NegateLight
                c1.type == CubeType.Dark && c2.type == CubeType.Light -> CubeType.NegateDark
                c1.type == CubeType.Dark && c2.type == CubeType.Dark -> CubeType.NegateDark
                c1.type == CubeType.NegateLight && c2.type == CubeType.Dark -> CubeType.NegateDark
                c1.type == CubeType.NegateLight && c2.type == CubeType.Light -> CubeType.NegateDark
                c1.type == CubeType.NegateDark && c2.type == CubeType.Light -> CubeType.NegateLight
                c1.type == CubeType.NegateDark && c2.type == CubeType.Dark -> CubeType.NegateLight
                else -> CubeType.Ignore
            }

            return Cube(x_range, y_range, z_range, type)
        }
        var cubes = mutableListOf<Cube>()
        for(i in input.indices) {
            val state = input[i].substringBefore(' ') == "on"
            val (x_range, y_range, z_range) = input[i].substringAfter(' ').split(',')
                .map { it.replace("x=","")
                    .replace("y=","")
                    .replace("z=","")}
                .map { IntRange(it.substringBefore("..").toInt(),it.substringAfter("..").toInt() )}
            var c2 = Cube(x_range, y_range, z_range, if (state) CubeType.Light else CubeType.Dark)
            var toAdd = mutableListOf<Cube>()
            for(c1 in cubes) {
                var inter = intersection(c1, c2)
                if (inter != null && inter.type != CubeType.Ignore) toAdd.add(inter)
            }
            cubes.addAll(toAdd)
            if (state) cubes.add(c2)
        }
        var count = 0L
        for(c in cubes) {
            when (c.type) {
                CubeType.Light -> count+= ((c.x_range.count()).toLong() * (c.y_range.count()) * (c.z_range.count()))
                CubeType.Dark -> count-= ((c.x_range.count()).toLong() * (c.y_range.count()) * (c.z_range.count()))
                CubeType.NegateDark -> count+= ((c.x_range.count()).toLong() * (c.y_range.count()) * (c.z_range.count()))
                CubeType.NegateLight -> count-= ((c.x_range.count()).toLong() * (c.y_range.count()) * (c.z_range.count()))
                CubeType.Ignore -> {}
            }
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day22_test")
    assert(part1(testInput) == 474140)
    assert(part2(testInput) == 2758514936282235)

    val input = readInput("Day22")
    println(part1(input))
    println(part2(input))
}
