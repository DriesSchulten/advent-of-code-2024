package schulten.me.aoc

val whitespaceRegex = "\\s+".toRegex()

fun text(file: String): String = resource(file).readText()

fun lines(file: String): List<String> = resource(file).readLines()

fun String.numbers(): List<Int> = this.split(whitespaceRegex).map(String::toInt)

private fun resource(file: String) = object {}.javaClass.getResourceAsStream(file)!!.bufferedReader(Charsets.UTF_8)