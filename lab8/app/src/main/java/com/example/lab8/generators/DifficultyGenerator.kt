package com.example.lab8.generators

class DifficultyGenerator {
    enum class Difficulties(val value: Int) {
        EASY(1), MEDIUM(2), HARD(3)
    }

    fun getDiff(correctAnswers: Int, totalAnswers: Int) : Int {
        var percentage = (correctAnswers.toDouble() / totalAnswers.toDouble() * 100).toInt()

        if(totalAnswers in 5..10) {
            if(percentage in 60..101) {
                return Difficulties.MEDIUM.value
            }
        }
        if(totalAnswers > 10){
            return when(percentage) {
                in 0..55 -> Difficulties.EASY.value
                in 55..90 -> Difficulties.MEDIUM.value
                else -> Difficulties.HARD.value
            }
        }
        return Difficulties.EASY.value;
    }
}