package day14

import utils.Utils
import java.io.File

class Robot(var pos : Pair<Int, Int>, val v : Pair<Int, Int>) {
    override fun toString(): String {
        return "position: " + pos.toString() + ", velocity: " + v.toString()
    }

    fun move(xMax : Int, yMax : Int) {
        var newX = (pos.first + v.first) % xMax
        var newY = (pos.second + v.second) % yMax

        if (newX < 0) newX = newX + xMax
        if (newY < 0) newY = newY + yMax

        pos = Pair(newX, newY)
    }
}

class Day14 (fileName : String) {
    private val data = File(fileName).readLines()
    private var robots : MutableList<Robot> = mutableListOf()
    private val u : Utils = Utils()

    init {
        for (str in data) {
            val posLst = str.split(" ")[0].split("=")[1].split(",").map { it.toInt() }
            val velLst = str.split(" ")[1].split("=")[1].split(",").map { it.toInt() }

            robots.add(Robot(Pair(posLst[0], posLst[1]), Pair(velLst[0], velLst[1])))
        }
    }

    private fun isRobotHere(x : Int, y : Int, maxX : Int, maxY : Int) : Boolean {
        if (x < 0 || y < 0 || x >= maxX || y >= maxY) return false

        for (robot in robots) {
            if (robot.pos.first == x && robot.pos.second == y) return true
        }

        return false
    }

    private fun nrOfNeighbours(robot : Robot, maxX: Int, maxY: Int) : Int {
        var result = 0

        val x = robot.pos.first
        val y = robot.pos.second

        for (i in x - 1 .. x + 1) {
            for (j in y - 1 .. y + 1) {
                if (!(i == x && j == y)) {
                    if (isRobotHere(i, j, maxX, maxY)) result += 1
                }
            }
        }

        return result
    }

    private fun robotsToMap(maxX : Int, maxY: Int) : List<String> {
        var result : MutableList<String> = mutableListOf()

        for (x in 0..< maxX) {
            var tmp = ""

            for (y in 0 ..< maxY) {
                if (isRobotHere(x, y, maxX, maxY)) tmp += '#'
                else tmp+= '.'
            }

            result.add(tmp)
        }

        return result
    }

    private fun robotsInInterval(minX : Int, maxX : Int, minY : Int, maxY : Int) : Int {
        var result = 0

        for (robot in robots) {
            if  (robot.pos.first in minX..maxX && robot.pos.second >= minY && robot.pos.second <= maxY) {
                result += 1
            }
        }

        return result
    }

    fun partOne() {
        val iterations = 100
        val xMax = 101
        val yMax = 103

        for (i in 1 .. iterations) {
            for (robot in robots) {
                robot.move(xMax, yMax)
            }
        }

        val xLimits = listOf(0, xMax / 2, xMax - 1)
        val yLimits = listOf(0, yMax / 2, yMax - 1)

        val q1 = robotsInInterval(xLimits[0], xLimits[1] - 1, yLimits[0], yLimits[1] - 1)
        val q2 = robotsInInterval(xLimits[1] + 1, xLimits[2], yLimits[0], yLimits[1])
        val q3 = robotsInInterval(xLimits[0], xLimits[1] - 1, yLimits[1] + 1, yLimits[2])
        val q4 = robotsInInterval(xLimits[1] + 1, xLimits[2], yLimits[1] + 1, yLimits[2])

        println("q1 = " + q1 + ", q2 = " + q2 + ", q3 = " + q3 + ", q4 = " + q4)
        println(q1 * q2 * q3 * q4)
    }

    fun partTwo() {
        val iterations = 10000
        val xMax = 101
        val yMax = 103

        var nMax = 0

        for (i in 1 .. iterations) {
            for (robot in robots) {
                robot.move(xMax, yMax)
            }

            var sumOfNeighbours = 0

            for (robot in robots) {
                sumOfNeighbours += nrOfNeighbours(robot, xMax, yMax)
            }

            if (sumOfNeighbours > nMax) {
                nMax = sumOfNeighbours
                println("-------------------------------------------------")
                println("Iteration: " + i)
                u.printMatrixStr(robotsToMap(xMax, yMax))
                println("--------------------------------------------------")
            }
        }
    }
}

fun main() {
    val d = Day14("data/day14.txt")

    d.partTwo()
}