package day4

import java.io.File

class Day4 (fileName : String) {
    private val data = File(fileName).readLines()

    private fun countXmas(row : Int, col : Int, remaining : String, rowDir : Int, colDir : Int) : Int {
        if (row + rowDir < 0 || row + rowDir > data.lastIndex) return 0
        if (col + colDir < 0 || col + colDir > data[0].lastIndex) return 0

        if (data[row + rowDir][col + colDir] != remaining[0]) return 0

        if (remaining.length == 1) {
            if (data[row + rowDir][col + colDir] == remaining[0]) return 1
            else return 0
        }

        return countXmas(row + rowDir, col + colDir, remaining.substring(1), rowDir, colDir)
    }

    fun countX_mas(row : Int, col : Int) : Int {
        var upLeft = data[row - 1][col - 1]
        var upRight = data[row - 1][col + 1]
        var downLeft = data[row + 1][col - 1]
        var downRight = data[row + 1][col + 1]

        var result = 0

        if ((upLeft + 'A'.toString() + downRight) == "MAS") result += 1
        if ((upRight + 'A'.toString() + downLeft) == "MAS") result += 1
        if ((downLeft + 'A'.toString() + upRight) == "MAS") result += 1
        if ((downRight + 'A'.toString() + upLeft) == "MAS") result += 1

        return result
    }

    fun partOne() {
        var result = 0

        for (row in data.indices) {
            for (col in data[row].indices) {
                if (data[row][col] == 'X') {
                    result += countXmas(row, col, "MAS", -1, 0)     // Up
                    result += countXmas(row, col, "MAS", -1, 1)     // Up right
                    result += countXmas(row, col, "MAS", 0, 1)      // Right
                    result += countXmas(row, col, "MAS", 1, 1)      // Down right
                    result += countXmas(row, col, "MAS", 1, 0)     // Down
                    result += countXmas(row, col, "MAS", 1, -1)    // Down left
                    result += countXmas(row, col, "MAS", 0, -1)     // Left
                    result += countXmas(row, col, "MAS", -1, -1)     // Up left
                }
            }
        }

        println(result)
    }

    fun partTwo() {
        var result = 0

        for (row in data.indices) {
            for (col in data[0].indices) {
                if (data[row][col] == 'A') {
                    if (row < 1 || row > data.lastIndex - 1) continue
                    if (col < 1 || col > data[0].lastIndex - 1) continue

                    if (countX_mas(row, col) > 1) result += 1
                }
            }
        }

        println(result)
    }
}

fun main() {
    val d = Day4("data/day4.txt")

    d.partTwo()
}