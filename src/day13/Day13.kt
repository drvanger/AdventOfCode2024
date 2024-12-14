package day13

import java.io.File

class Machine (val btnA : Pair<Long, Long>, val btnB : Pair<Long, Long>, val prize : Pair<Long, Long>) {
    override fun toString(): String {
        return "A - " + btnA.toString() + ", B - " + btnB.toString() + ", prize: " + prize.toString()
    }
}

class Day13 (fileName : String) {
    private val data = File(fileName).readLines()
    private var machines : MutableList<Machine> = mutableListOf()

    init {
        var btA : Pair<Long, Long> = Pair(-1, -1)
        var btB : Pair<Long, Long> = Pair(-1, -1)
        var prz : Pair<Long, Long> = Pair(-1, -1)

        for (str in data) {
            val splitted = str.split(" ")

            if (splitted.size == 1) continue

            if (splitted[1] == "A:") {
                val x = splitted[2].substring(2, splitted[2].lastIndex).toLong()
                val y = splitted[3].substring(2, splitted[3].lastIndex + 1).toLong()

                btA = Pair(x, y)
            }
            else if (splitted[1] == "B:") {
                val x = splitted[2].substring(2, splitted[2].lastIndex).toLong()
                val y = splitted[3].substring(2, splitted[3].lastIndex + 1).toLong()

                btB = Pair(x, y)
            }
            else {
                val x = splitted[1].substring(2, splitted[1].lastIndex).toLong()
                val y = splitted[2].substring(2, splitted[2].lastIndex + 1).toLong()

                prz = Pair(x, y)

                val tmp = Machine(btA, btB, prz)
                machines.add(tmp)
            }
        }
    }

    private fun calcBruteforce(m : Machine) : Long {
        for (b in 0 .. 100) {
            for (a in 0 .. 100) {
                val posX = b * m.btnB.first + a * m.btnA.first
                val posY = b * m.btnB.second + a * m.btnA.second

                if (posX == m.prize.first && posY == m.prize.second) return b + 3.toLong() * a
            }
        }

        return 0
    }

    private fun calcFormula(m : Machine) : Long {
        var x : Long = (m.btnB.second * m.prize.first - m.btnB.first * m.prize.second) / (m.btnB.second * m.btnA.first - m.btnB.first * m.btnA.second)
        var y = (m.prize.second - m.btnA.second * x) / m.btnB.second

        println(x.toString() + ", " + y)

        if (x * m.btnA.first + y * m.btnB.first != m.prize.first || x * m.btnA.second + y * m.btnB.second != m.prize.second) {
            x = 0.toLong()
            y = 0.toLong()
        }

        return 3.toLong() * x + y
    }

    fun partOne() {
        var result = 0.toLong()

        for (m in machines) {
            result += calcBruteforce(m)
        }

        println(result)
    }

    fun partTwo() {
        var result = 0.toLong()

        for (m in machines) {
            val m2 = Machine(m.btnA, m.btnB, Pair(m.prize.first + 10000000000000, m.prize.second + 10000000000000))

            result += calcFormula(m2)
        }

        println(result)
    }
}

fun main() {
    val d = Day13("data/day13_levi.txt")

    d.partTwo()
}