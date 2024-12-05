package day5

import java.io.File
import java.util.Deque
import java.util.LinkedList
import java.util.Queue

class Day5 (fileName : String) {
    private val data = File(fileName).readLines()

    private var ruleGraph : HashMap<Int, MutableSet<Int>> = hashMapOf()
    private var updates : MutableList<List<Int>> = mutableListOf()
    private var inCount : HashMap<Int, Int> = hashMapOf()
    private var topOrder : MutableList<Int> = mutableListOf()

    init {
        var readUpdates = false

        for (str in data) {
            if (readUpdates) {
                val update = str.split(',').map { it.toInt() }
                updates.add(update)
            }
            else if (str == "") readUpdates = true
            else {
                val rule = str.split('|').map { it.toInt() }

                if (inCount.containsKey(rule[1])) inCount[rule[1]] = inCount[rule[1]]!! + 1
                else inCount[rule[1]] = 1

                if (!inCount.containsKey(rule[0])) inCount[rule[0]] = 0

                if (ruleGraph.containsKey(rule[0])) ruleGraph[rule[0]]!!.add(rule[1])
                else {
                    var tmp : MutableSet<Int> = mutableSetOf()
                    tmp.add(rule[1])

                    ruleGraph[rule[0]] = tmp
                }
            }
        }

        // Calculating the topological order
        var q : Queue<Int> = LinkedList()

        for (k in inCount.keys) {
            if (inCount[k] == 0) q.add(k)
        }

        while(q.isNotEmpty()) {
            var current = q.remove()

            topOrder.add(current)

            if (topOrder.size == inCount.size) break

            for (neighbour in ruleGraph[current]!!) {
                inCount[neighbour] = inCount[neighbour]!! - 1

                if (inCount[neighbour]!! == 0) q.add(neighbour)
            }
        }
    }

    private fun topSort(update : List<Int>) : List<Int> {
        var currentInCount : HashMap<Int, Int> = hashMapOf()

        for (item in update) {
            currentInCount[item] = 0
        }

        for (rule in ruleGraph) {
            if (update.contains(rule.key)) {
                for (destination in rule.value) {
                    if (update.contains(destination)) {
                        currentInCount[destination] = currentInCount[destination]!! + 1
                    }
                }
            }
        }

        // Calculating the topological order
        var order : MutableList<Int> = mutableListOf()
        var q : Queue<Int> = LinkedList()

        for (k in currentInCount.keys) {
            if (currentInCount[k] == 0) q.add(k)
        }

        while(q.isNotEmpty()) {
            var current = q.remove()

            order.add(current)

            if (order.size == currentInCount.size) break

            for (neighbour in ruleGraph[current]!!) {
                if (update.contains(neighbour)) {
                    currentInCount[neighbour] = currentInCount[neighbour]!! - 1

                    if (currentInCount[neighbour]!! == 0) q.add(neighbour)
                }
            }
        }

        return order
    }

    private fun isCorrect(update : List<Int>) : Boolean {
        for (i in update.indices) {
            val item = update[i]

            if (!ruleGraph.keys.contains(item)) continue

            for (rule in ruleGraph[item]!!) {
                for (j in 0 .. i) {
                    if (update[j] == rule) return false
                }
            }
        }

        return true
    }

    fun partOne() {
        var result = 0

        for (update in updates) {
            if (isCorrect(update)) result += update[update.size / 2]
        }

        println(result)
    }

    fun partTwo() {
        var result = 0

        for (update in updates) {
            if (!isCorrect(update)) {
                //println(topSort(update))

                result += topSort(update)[update.size / 2]
            }
        }

        println(result)
    }
}

fun main() {
    val d = Day5("data/day5.txt")

    d.partTwo()
}