package com.example.lab4

import android.util.Log

class DifficultyGenerator {
    enum class Difficulties(val multiplier: Int) {
        EASY(1), MEDIUM(2), HARD(3)
    }


    fun getDiff(correctAnswers: Int, totalAnswers: Int) : Int {
        var percentage = (correctAnswers.toDouble() / totalAnswers.toDouble() * 100).toInt()
        Log.i("percentage", percentage.toString())
        if(totalAnswers in 5..10) {
            if(percentage in 60..101) {
                return Difficulties.MEDIUM.multiplier
            }
        }
        if(totalAnswers > 10){
            return when(percentage) {
                in 0..55 -> Difficulties.EASY.multiplier
                in 55..90 -> Difficulties.MEDIUM.multiplier
                else -> Difficulties.HARD.multiplier
            }
        }
        return Difficulties.EASY.multiplier;
    }

}