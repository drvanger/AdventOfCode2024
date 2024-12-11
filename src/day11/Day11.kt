package day11

import java.io.File

class Day11 (fileName : String) {
    private val data = File(fileName).readLines()[0].split(" ").map { it.toLong() }

    private fun splitNumber(number : Long) : Pair<Long, Long> {
        val str = number.toString()

        val nr1 = str.substring(0, str.length / 2)
        val nr2 = str.substring(str.length / 2)

        return Pair(nr1.toLong(), nr2.toLong())
    }

    fun calculate(number : Long, iterations : Int, memo : HashMap<Long, HashMap<Int, Long>>) : Long {
        if (iterations == 0) return 1

        if (memo.contains(number) && memo[number]!!.containsKey(iterations - 1)) return memo[number]!![iterations - 1]!!

        if (number == 0.toLong()) {
            val result = calculate(1, iterations - 1, memo)

            if (memo.containsKey(number)) memo[number]!![iterations - 1] = result
            else {
                memo[number] = hashMapOf()
                memo[number]!![iterations - 1] = result
            }

            return result
        }
        else if (number.toString().length % 2 == 0) {
            val newNrs = splitNumber(number)

            val result = calculate(newNrs.first, iterations - 1, memo) + calculate(newNrs.second, iterations - 1, memo)

            if (memo.containsKey(number)) memo[number]!![iterations - 1] = result
            else {
                memo[number] = hashMapOf()
                memo[number]!![iterations - 1] = result
            }

            return result
        }
        else {
            val result = calculate(number * 2024.toLong(), iterations - 1, memo)

            if (memo.containsKey(number)) memo[number]!![iterations - 1] = result
            else {
                memo[number] = hashMapOf()
                memo[number]!![iterations - 1] = result
            }

            return result
        }
    }

    fun partTwo() {
        var result = 0.toLong()

        var iterations = 75

        var memo : HashMap<Long, HashMap<Int, Long>> = hashMapOf()

        for (item in data) {
            result += calculate(item, iterations, memo)
        }

        println(result)
    }

    fun partOne() {
        val iterations = 25

        var current : MutableList<Long> = mutableListOf()

        for (item in data) {
            current.add(item)
        }

        for (i in 1 .. iterations) {
            println("Iteration: " + i)

            var tmp : MutableList<Long> = mutableListOf()

            for (item in current) {
                if (item == 0.toLong()) tmp.add(1)
                else if (item.toString().length % 2 == 0) {
                    val newNrs = splitNumber(item)

                    tmp.add(newNrs.first)
                    tmp.add(newNrs.second)
                }
                else {
                    tmp.add(item * 2024)
                }
            }

            current = tmp
        }

        println(current.size)
    }
}

fun main() {
    val d = Day11("data/day11.txt")

    d.partTwo()
}