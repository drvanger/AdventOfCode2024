package template

import java.io.File

class Template (fileName : String) {
    private val data = File(fileName).readLines()

    init {
        for (str in data) {
            println(str)
        }
    }
}

fun main() {
    val d = Template("/home/duri/intellij-projects/AdventOfCode2023/data/day1_test.txt")
}