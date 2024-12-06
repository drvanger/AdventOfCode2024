package day6

import utils.Utils
import java.io.File

class Day6 (fileName : String) {
    private val data = File(fileName).readLines()

    fun findPos() : Pair <Int, Int> {
        for (row in data.indices)
        {
            for (col in data[row].indices) {
                if (data[row][col] == '^') return Pair(row, col)
            }
        }

        return Pair(-1, -1)
    }

    private fun putObstruction(row : Int, col : Int) : List<String> {
        var newMap : MutableList<String> = mutableListOf()

        for (r in data.indices) {
            if (r != row ) newMap.add(data[r])
            else {
                var tmp = ""
                for (c in data[r].indices) {
                    if (c != col) tmp += data[r][c]
                    else tmp += "#"
                }
                newMap.add(tmp)
            }
        }

        return newMap
    }

    private fun isLoop(map : List<String>) : Boolean {
        var position = findPos()
        var direction = Pair(-1, 0)

        var visited : MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>> = mutableSetOf()

        visited.add(Pair(position, direction))

        while (isInsideMap(position)) {
            val nextRow = position.first + direction.first
            val nextCol = position.second + direction.second

            if (!isInsideMap(Pair(nextRow, nextCol))) {
                break
            }

            if (map[nextRow][nextCol] != '#') {
                position = Pair(nextRow, nextCol)

                if (visited.contains(Pair(position, direction))) return true
                else visited.add(Pair(position, direction))
            }
            else direction = nextDir(direction)
        }

        return false
    }

    fun isInsideMap(position : Pair <Int, Int>) : Boolean {
        return position.first >= 0 && position.first <= data.lastIndex && position.second >= 0 && position.second <= data[0].lastIndex
    }

    /*
    UP: -1, 0
    RIGHT: 0, 1
    DOWN: 1, 0
    LEFT: 0, -1
     */
    fun nextDir(direction : Pair<Int, Int>) : Pair<Int, Int> {
        if (direction.first == -1 && direction.second == 0) {
            return Pair(0, 1)
        }
        else if (direction.first == 0 && direction.second == 1) {
            return Pair(1, 0)
        }
        else if (direction.first == 1 && direction.second == 0) {
            return Pair(0, -1)
        }
        else {
            return Pair(-1, 0)
        }
    }

    fun partOne() {
        var visited : MutableSet<Pair<Int, Int>> = mutableSetOf()
        var position = findPos()
        var direction = Pair(-1, 0)

        visited.add(position)

        while (isInsideMap(position)) {
            val nextRow = position.first + direction.first
            val nextCol = position.second + direction.second

            if (!isInsideMap(Pair(nextRow, nextCol))) {
                break
            }

            if (data[nextRow][nextCol] != '#') {
                position = Pair(nextRow, nextCol)
                visited.add(position)
            }
            else direction = nextDir(direction)

        }

        println(visited.size)
    }

    fun partTwo() {
        var result = 0

        for (row in data.indices) {
            for (col in data[row].indices) {
                val newMap = putObstruction(row, col)

                if (isLoop(newMap)) result += 1
            }
        }

        println(result)
    }
}

fun main() {
    val d = Day6("data/day6.txt")

    d.partTwo()
}