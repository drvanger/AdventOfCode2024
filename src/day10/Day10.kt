package day10

import java.io.File

class Day10 (fileName : String) {
    private val data = File(fileName).readLines().map { it.map { it.digitToInt() } }

    private fun findRoutes(row : Int, col : Int, nextNumber : Int, peaks : MutableSet<Pair<Int, Int>>) {
        if (nextNumber == 9) {
            if (data[row][col] == 9) {
                peaks.add(Pair(row, col))
                return
            }
            else return
        }

        if (data[row][col] != nextNumber) return

        val next = nextNumber + 1

        if (row > 0)  findRoutes(row - 1, col, next, peaks)
        if (col < data[row].lastIndex)  findRoutes(row, col + 1, next, peaks)
        if (row < data.lastIndex)  findRoutes(row + 1, col, next, peaks)
        if (col > 0)  findRoutes(row, col - 1, next, peaks)

        return
    }

    private fun findRoutes2(row : Int, col : Int, nextNumber: Int) : Int {
        if (nextNumber == 9) {
            if (data[row][col] == 9) {
                return 1
            }
            else return 0
        }

        if (data[row][col] != nextNumber) return 0

        val next = nextNumber + 1

        var result = 0

        if (row > 0) result += findRoutes2(row - 1, col, next)
        if (col < data[row].lastIndex)  result += findRoutes2(row, col + 1, next)
        if (row < data.lastIndex)  result += findRoutes2(row + 1, col, next)
        if (col > 0)  result += findRoutes2(row, col - 1, next)

        return result
    }

    fun partOne() {
        var result = 0

        for (row in data.indices) {
            for (col in data[row].indices) {
                if (data[row][col] == 0) {
                    val peaks : MutableSet<Pair<Int, Int>> = mutableSetOf()

                    findRoutes(row, col, 0, peaks)

                    result += peaks.size
                }
            }
        }

        println(result)
    }

    fun partTwo() {
        var result = 0

        for (row in data.indices) {
            for (col in data[row].indices) {
                if (data[row][col] == 0) {
                    result += findRoutes2(row, col, 0)
                }
            }
        }

        println(result)
    }
}

fun main() {
    val d = Day10("data/day10.txt")

    d.partTwo()
}