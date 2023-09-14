package com.example.lab4

import kotlin.math.pow
import kotlin.random.Random


class RandomEquationGenerator {
    fun generate(diff: Int): Equation {
        val base = 10
        val rangeEnd = base.toDouble().pow(diff).toInt()
        val operation = getRandomOperation()
        val num1: Int = Random.nextInt(1, rangeEnd)
        val num2: Int = Random.nextInt(1, rangeEnd)
        val answer = calculateEquation(num1, num2, operation)
        val stringValue = "$num1 $operation $num2 = ?"

        return Equation(stringValue,answer)
    }


    private fun getRandomOperation() : String{
        val operationsList: ArrayList<String> = ArrayList(listOf("+","-","*"))
        return operationsList.shuffled()[Random.nextInt(0,operationsList.size - 1)]
    }

    private fun calculateEquation(num1: Int, num2: Int, operation: String) : Int {
        when(operation) {
            "+" -> return num1 + num2
            "-" -> return num1 - num2
            "*" -> return num1 * num2
        }
        return Int.MAX_VALUE
    }



}