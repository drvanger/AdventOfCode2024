package day8

import java.io.File

class Day8 (fileName : String) {
    private val data = File(fileName).readLines()
    private var antennas : MutableMap<Char, MutableList<Pair<Int, Int>>> = mutableMapOf()

    init {
        for (row in data.indices) {
            for (col in data[row].indices) {
                if (data[row][col] != '.') {
                    if (antennas.containsKey(data[row][col])) {
                        antennas[data[row][col]]!!.add(Pair(row, col))
                    }
                    else {
                        var tmp : MutableList<Pair<Int, Int>> = mutableListOf()
                        tmp.add(Pair(row, col))
                        antennas[data[row][col]] = tmp
                    }
                }
            }
        }
    }

    private fun isInMap(point : Pair<Int, Int>) : Boolean {
        return point.first >= 0 && point.first <= data.lastIndex && point.second >= 0 && point.second <= data[0].lastIndex
    }

    private fun getAntinodes(a1 : Pair<Int, Int>, a2 : Pair<Int, Int>) : List<Pair<Int, Int>> {
        var result : MutableList<Pair<Int, Int>> = mutableListOf()

        val dRow = a2.first - a1.first
        val dCol = a2.second - a1.second

        val node1 = Pair(a1.first - dRow, a1.second - dCol)
        val node2 = Pair(a2.first + dRow, a2.second + dCol)

        if (isInMap(node1)) result.add(node1)
        if (isInMap(node2)) result.add(node2)

        return result
    }

    private fun getAntinodes2(a1 : Pair<Int, Int>, a2 : Pair<Int, Int>) : List<Pair<Int, Int>> {
        var result : MutableList<Pair<Int, Int>> = mutableListOf()

        val dRow = a2.first - a1.first
        val dCol = a2.second - a1.second

        var currentNode = Pair(a1.first, a1.second)

        while (isInMap(currentNode)) {
            result.add(currentNode)

            currentNode = Pair(currentNode.first - dRow, currentNode.second - dCol)
        }

        currentNode = Pair(a2.first, a2.second)

        while (isInMap(currentNode)) {
            result.add(currentNode)

            currentNode = Pair(currentNode.first + dRow, currentNode.second + dCol)
        }

        return result
    }

    fun partOne() {
        var nodes : MutableSet<Pair<Int, Int>> = mutableSetOf()

        for (fr in antennas.keys) {
            for (a1 in antennas[fr]!!.indices) {
                for (a2 in a1 .. antennas[fr]!!.lastIndex) {
                    if (a1 != a2) {
                        val an1 = antennas[fr]!![a1]
                        val an2 = antennas[fr]!![a2]

                        val currentNodes = getAntinodes(an1, an2)

                        for (node in currentNodes) {
                            nodes.add(node)
                        }
                    }
                }
            }
        }

        println(nodes.size)
    }

    fun partTwo() {
        var nodes : MutableSet<Pair<Int, Int>> = mutableSetOf()

        for (fr in antennas.keys) {
            for (a1 in antennas[fr]!!.indices) {
                for (a2 in a1 .. antennas[fr]!!.lastIndex) {
                    if (a1 != a2) {
                        val an1 = antennas[fr]!![a1]
                        val an2 = antennas[fr]!![a2]

                        val currentNodes = getAntinodes2(an1, an2)

                        for (node in currentNodes) {
                            nodes.add(node)
                        }
                    }
                }
            }
        }

        println(nodes.size)
    }
}

fun main() {
    val d = Day8("data/day8.txt")

    d.partTwo()
}