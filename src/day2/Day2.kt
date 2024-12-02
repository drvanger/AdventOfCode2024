package template

import java.io.File
import kotlin.math.abs
import kotlin.math.sign

class Day2 (fileName : String) {
    private val data = File(fileName).readLines().map { it.split(" ").map { it.toInt() } }

    private fun isSafe  (report : List<Int>) : Boolean {
        //println(report)

        var sign = 0

        for (i in 1 .. report.lastIndex) {
            if (sign == 0) {
                if (i > 1) return false

                sign = (report[i] - report [i-1]).sign
            }

            if ((report[i] - report [i-1]).sign != sign) return false

            if (abs(report[i] - report [i-1]) < 1 ||  abs(report[i] - report [i-1]) > 3) {
                return false
            }
        }

        return true
    }

    private fun isSafe2  (report : List<Int>) : Boolean {
        if (isSafe(report)) return true

        for (i in report.indices) {
            if (isSafe(report.filterIndexed { index, it -> index != i })) return true
        }

        return false
    }

    fun partOne() {
        println(data.map { isSafe(it) }.filter { it }.size)
    }

    fun partTwo() {
        println(data.map { isSafe2(it) }.filter { it }.size)

        //println(data.map { isSafe2(it) })
    }
}

fun main() {
    val d = Day2("data/day2.txt")

    d.partTwo()
}