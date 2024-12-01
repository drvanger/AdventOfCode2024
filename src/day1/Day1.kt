package day1

import java.io.File
import kotlin.math.abs

class Day1 (fileName : String) {
    private val data = File(fileName).readLines().map { it.split("   ").map { it.toInt() } }
    private val dataLists : MutableList<MutableList<Int>> = mutableListOf()

    init {
        var list1 : MutableList<Int> = mutableListOf()
        var list2 : MutableList<Int> = mutableListOf()

        for (pair in data) {
            list1.add(pair[0])
            list2.add(pair[1])
        }

        dataLists.add(list1)
        dataLists.add(list2)

        //println(dataLists)
    }

    fun partOne() {
        var sortedData = listOf(dataLists[0].sorted(), dataLists[1].sorted())

        var result = 0

        for (i in sortedData[0].indices) {
            result += abs((sortedData[0][i] - sortedData[1][i]))
        }

        println(result)
    }

    fun partTwo() {
        var result = 0

        for (number in dataLists[0]) {
            result += number * dataLists[1].filter { it == number }.size
        }

        println(result)
    }
}

fun main() {
    val d = Day1("data/day1.txt")

    d.partTwo()
}