package day9

import java.io.File
import java.util.Deque
import java.util.LinkedList

class Day9 (fileName : String) {
    private val data = File(fileName).readLines()

    fun partOne() {
        var d : Deque<Int> = LinkedList()

        var isFile = true
        var id = 0

        for (ch in data[0]) {
            val number = ch.digitToInt()

            if (isFile) {
                for (i in 0..< number) {
                    d.addLast(id)
                }

                id += 1
            }
            else {
                for (i in 0..< number) {
                    d.addLast(-1)
                }
            }

            isFile = !isFile
        }

        var ordered : MutableList<Int> = (List(d.size) {0}).toMutableList()

        // Ordering
        var i = 0

        while (d.isNotEmpty()) {
            //println(ordered)

            var tmp = d.removeFirst()

            if (tmp != -1) {
                ordered[i] = tmp
                i += 1
            }
            else {
                while (tmp == -1) {
                    if (d.isEmpty()) break

                    tmp = d.removeLast()
                }

                if (tmp == -1) {
                    ordered[i] = 0
                }
                else {
                    ordered[i] = tmp
                }
                i += 1
            }
        }

        // Calculating the checksum
        var result = 0.toLong()

        for (j in ordered.indices) {
            result += j * ordered[j]
        }

        println(result)

    }

    fun partTwo() {
        class Space(var isEmpty: Boolean, var size: Int, val id: Int) {
            override fun toString(): String {
                var str = ""

                if (isEmpty) {
                    for (i in 1 .. size) str += "."
                }
                else {
                    for (i in 1 .. size) str += id.toString()
                }

                return str
            }
        }

        var isFile = true
        var id = 0

        var fs : MutableList<Space> = mutableListOf()

        for (ch in data[0]) {
            val number = ch.digitToInt()

            if (number == 0) {
                if (isFile) id += 1

                isFile = !isFile
                continue
            }

            if (isFile) {
                fs.add(Space(false, number, id))

                id += 1
            } else {
                fs.add(Space(true, number, id))
            }

            isFile = !isFile
        }

        // Ordering of files
        var i = fs.lastIndex
        var moved : MutableSet<Int> = mutableSetOf()

        while (i > 0) {
            if (fs[i].isEmpty) {
                i -= 1
                continue
            }

            if (moved.contains(fs[i].id)) {
                i -= 1
                continue
            }

            for (j in 0..< i) {
                if (fs[j].isEmpty && fs[j].size >= fs[i].size) {
                    fs[j].size -= fs[i].size

                    val tmp = Space(false, fs[i].size, fs[i].id)
                    fs[i].isEmpty = true
                    i += 1

                    fs.add(j, tmp)

                    moved.add(tmp.id)

                    break
                }
            }

            i -= 1
        }

        // Constructing an array representation of the fs
        var fsFiltered = fs.filter { it.size > 0 }

        var fsList : MutableList<Int> = mutableListOf()

        for (file in fsFiltered) {
            if (file.isEmpty) {
                fsList += List(file.size) {0}
            }
            else {
                fsList += List(file.size) {file.id}
            }
        }

        // Calculating the checksum
        var result = 0.toLong()

        for (index in fsList.indices) {
            result += index * fsList[index]
        }

        println(result)
    }
}

fun main() {
    val d = Day9("data/day9.txt")

    d.partTwo()
}