package com.example.lab8.generators

import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class RandomNumbersGenerator {

    fun generateNumbersDependsOnTarget(target: Int): ArrayList<Int> {
        val numbersList : ArrayList<Int> = ArrayList()

        val base = 10
        val rank = getIntDigitsLength(target)
        val rangeStart = base.toDouble().pow(rank - 1).toInt()
        val rangeEnd = base.toDouble().pow(rank).toInt()

        val range = if(target >=0) rangeStart..rangeEnd else -rangeEnd..-rangeStart

        while(numbersList.size != 3){
            val randomInt = range.random()
            if(randomInt != target && !numbersList.contains(randomInt)) {
                numbersList.add(randomInt)
            }
        }

        return numbersList
    }

    private fun getIntDigitsLength(target: Int) : Int {

        return if(target >= 0) floor(log10(target.toDouble()) + 1).toInt()
        else floor(log10(-target.toDouble()) + 1).toInt()
    }

}