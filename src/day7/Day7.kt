package day7

import java.io.File

class Day7 (fileName : String) {
    private val data = File(fileName).readLines().map { it.split(" ").map { it.filter { it.isDigit() } }.map { it.toLong() }}

    private val results = data.map { it[0] }
    private val items = data.map { it.subList(1, it.lastIndex + 1) }

    private fun calculate (start : Long, next : Long, operator : Char) : Long {
        if (operator == '+') return start + next
        else if (operator == '*') return start * next
        else {
            return (start.toString() + next.toString()).toLong()
        }
    }

    private fun canBeValid(operators : List<Char>, startingValue : Long, remaining : List<Long>, expected : Long) : Boolean {
        if (remaining.isEmpty()) {
            if (expected == startingValue) return true
            else return false
        }

        for (op in operators) {
            if (canBeValid(operators, calculate(startingValue, remaining[0], op), remaining.subList(1, remaining.lastIndex + 1), expected)) return true
        }

        return false
    }

    fun partOne() {
        var result : Long = 0

        for (i in items.indices) {
            if(canBeValid(listOf('+', '*'), items[i][0], items[i].subList(1, items[i].lastIndex + 1), results[i])) {
                result += results[i]
            }
        }

        println(result)
    }

    fun partTwo() {
        var result : Long = 0

        for (i in items.indices) {
            if(canBeValid(listOf('+', '*', '|'), items[i][0], items[i].subList(1, items[i].lastIndex + 1), results[i])) {
                result += results[i]
            }
        }

        println(result)
    }
}

fun main() {
    val d = Day7("data/day7.txt")

    d.partOne()
}