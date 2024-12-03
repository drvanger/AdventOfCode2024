package day3

import java.io.File

class Day3 (fileName : String) {
    private val data = File(fileName).readLines().toString()

    // I know that regexp would be the easiest solution, but I'm not using it because of a bet
    private fun readMul(index : Int) : Pair<Int, Pair<Int, Int>> {
        if (data[index + 3] != '(') return Pair(-1, Pair(-1, -1))

        var currentIndex = index + 4

        var nr1 = -1
        var nr2 = -1

        var nr1_str = ""
        var nr2_str = ""

        // Reading in the first number
        while (true) {
            if (data[currentIndex].isDigit()) {
                nr1_str += data[currentIndex]
                currentIndex += 1
            }
            else if (data[currentIndex] == ',') {
                currentIndex += 1
                break
            }
            else return Pair(-1, Pair(-1, -1))
        }

        // Reading in the second number
        while (true) {
            if (data[currentIndex].isDigit()) {
                nr2_str += data[currentIndex]
                currentIndex += 1
            }
            else if (data[currentIndex] == ')') {
                nr1 = nr1_str.toInt()
                nr2 = nr2_str.toInt()

                return Pair(currentIndex, Pair(nr1, nr2))
            }
            else return Pair(-1, Pair(-1, -1))
        }
    }

    fun partOne() {
        var pairs : MutableList<Pair<Int, Int>> = mutableListOf()

        var i = 0

        var isEnabled = true

        while (i < data.lastIndex - 3) {
            if (data.substring(i, i+3) == "mul" && isEnabled) {
                val p = readMul(i)

                if (p.first == -1) {
                    i += 1
                }
                else {
                    pairs.add(p.second)
                    i = p.first
                }
            }

            if (i + 4 <= data.lastIndex && data.substring(i, i+4) == "do()") {
                isEnabled = true
            }

            if (i + 7 <= data.lastIndex && data.substring(i, i+7) == "don't()") {
                isEnabled = false
            }

            i += 1
        }

        var result = 0

        for (p in pairs) {
            result += (p.first * p.second)
        }

        println(result)
    }
}

fun main() {
    val d = Day3("data/day3.txt")

    d.partOne()
}