package com.flexilue.hfu.adventofcode

import org.junit.Assert
import org.junit.Test
import java.io.File

class Day2 {

    /*
    @Test
    fun `solution 1`() {
        val lines = File("src/test/Day2Input.txt").readLines()
        val input: List<Command> = lines.map {line ->
            val parts: List<String> = line.split(" ")
            Command(parts.first(), parts[1].toInt())
        }


        var horizontal = 0
        var depth = 0

        input.forEach{ command ->
            when(command.name){
                "forward" -> horizontal += command.delta
                "up" -> depth -= command.delta
                "down" -> depth += command.delta
            }
        }

        val result = horizontal * depth
        Assert.assertEquals(result, 150)
    }
    */

    @Test
    fun `solution 2`() {
        val lines = File("src/test/Day2Input.txt").readLines()
        val input: List<Command> = lines.getCommands()

        val finalPosition = input.fold(Position(0,0, 0)){ result, command ->
            when(command){
                is Command.Forward -> result.copy(horizontal = result.horizontal + command.delta, depth = result.depth + (result.aim * command.delta))
                is Command.Up -> result.copy(aim = result.aim - command.delta)
                is Command.Down -> result.copy(aim = result.aim + command.delta)
            }
        }

        val result = finalPosition.total
        Assert.assertEquals(result, 900)
    }
    fun getDelta(command: Command): Int {
        return command.delta
    }

    sealed class Command {
        open val delta: Int = 0

        class Forward(override val delta: Int): Command()
        class Up(override val delta: Int): Command()
        class Down (override val delta: Int): Command()
    }

    data class Position(val horizontal: Int, val depth: Int, val aim: Int){
        //val total get() = horizontal * depth
        //fun calculateTotal() = horizontal * depth
    }

    //Extension Funktion -> damit data Class keine Funktion im Body hat (gut für Standard Library)
    fun Position.calculateTotal(): Int {
        return horizontal * depth
    }

    val Position.total: Int get() = horizontal * depth

    //Extension Function für Standard List<String> für Lesbarkeit
    fun List<String>.getCommands(): List<Command>{
        return mapNotNull {line ->
            val parts: List<String> = line.split(" ")
            val delta = parts[1].toInt()
            when(parts.first()){
                "forward" -> Command.Forward(delta)
                "up" -> Command.Up(delta)
                "down" -> Command.Down(delta)
                else -> null
            }
        }
    }
}