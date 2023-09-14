package com.example.lab4

import kotlin.math.pow
import kotlin.random.Random


class RandomEquationGenerator {
    fun generate(diff: Int): Equation {
        var base : Int = 10;
        var range = base.toDouble().pow(diff.toDouble()).toInt()
        val operation = getRandomOperation()
        val num1: Int = Random.nextInt(1, range);
        val num2: Int = Random.nextInt(1, range)
        var answer = calculateEquation(num1, num2, operation)
        var stringValue = "$num1 $operation $num2 = ?";

        return Equation(stringValue,answer);
    }


    private fun getRandomOperation() : String{
        val operationsList: ArrayList<String> = ArrayList(listOf("+","-","*"))
        return operationsList.shuffled()[Random.nextInt(0,operationsList.size - 1)];
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