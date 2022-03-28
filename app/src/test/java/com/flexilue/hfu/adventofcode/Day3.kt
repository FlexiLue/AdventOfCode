package com.flexilue.hfu.adventofcode

import android.media.Rating
import org.junit.Assert
import org.junit.Test
import java.io.File

class Day3 {
    @Test
    fun `solution 1`() {
        val lines: List<String> = File("src/test/Day3Input.txt").readLines()
        val columns = lines[0].indices
        val oxygenRating = rating(lines, RatingType.OXYGEN)
        val co2Rating = rating(lines, RatingType.CO2)

        val gammaRate = buildString {
            for(column in columns) {
                val (zeros, ones) = lines.countBitsInColumn(column)
                val commonBit = if (zeros > ones) "1" else "0"
                this.append(commonBit)
            }
        }
        val epsilonRate = gammaRate.invertBinaryString()
        val result = gammaRate.toInt(2) * epsilonRate.toInt(2)
        val result2 = oxygenRating.toInt(2) * co2Rating.toInt(2)
        Assert.assertEquals(result2, 230)
    }
    private fun List<String>.countBitsInColumn(column: Int): BitCount{
        var zeros = 0
        var ones = 0
        for(line in this){
            if(line[column] == '0') zeros++ else ones++
        }
        return BitCount(zeros, ones)
    }


    private fun String.invertBinaryString(): String{
        return this.asIterable().joinToString(""){ if(it == '1') "0" else "1" }
    }

    private fun rating(list: List<String>, type: RatingType): String{
        val columns = list[0].indices
        var candidates = list
        for(column in columns){
            val (zeros, ones) = candidates.countBitsInColumn(column)
            val commonBit = if(zeros > ones) '0' else '1'
            candidates = candidates.filter {
                when(type){
                    RatingType.OXYGEN -> it[column] == commonBit
                    RatingType.CO2 -> it[column] != commonBit
                }
            }
            if(candidates.size == 1) break
        }
        return candidates.single()
    }

    enum class RatingType {
        OXYGEN,
        CO2
    }

    data class BitCount(val zeros: Int, val ones: Int)
}