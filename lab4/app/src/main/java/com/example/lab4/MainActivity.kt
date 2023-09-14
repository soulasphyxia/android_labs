package com.example.lab4

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var correctAnswersCount: Int = 0;
    private var incorrectAnswersCount: Int = 0;
    private var totalAnswersCount: Int = 1;
    private val randomEquationGenerator = RandomEquationGenerator()
    private val difficultyGenerator = DifficultyGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBaseViews()
    }

    private fun initBaseViews() {
        updateTextViews()
        generateTask()
    }

    private fun generateTask() {
        var diff = difficultyGenerator.getDiff(correctAnswersCount, totalAnswersCount)
        var range = 10.toDouble().pow(diff.toDouble()).toInt()

        Log.i("difficult", diff.toString())
        var equation : Equation = randomEquationGenerator.generate(diff)
        Log.i("equation", equation.stringValue)

        val answers : ArrayList<Int> = ArrayList(listOf(
            equation.answer,
            Random.nextInt(0, range),
            Random.nextInt(0, range),
        ))

        renderTask(equation, answers.shuffled())
    }

    private fun renderTask(equation: Equation, answers: List<Int>) {

        val listView = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter<Int>(this, R.layout.my_list_item, answers)
        listView.adapter = adapter
        listView.onItemClickListener = OnItemClickListener {  parent, view, position, id  ->
            totalAnswersCount++
            var chosenAnswer = answers[position];
            val correct : Boolean  = chosenAnswer == equation.answer
            if(correct) correctAnswersCount++ else incorrectAnswersCount++
            val toast: Toast = Toast.makeText(this,
                if(correct) "Правильный ответ!" else "Неправильный ответ!",
                Toast.LENGTH_SHORT)
            toast.show()
            generateTask()
        }

        val textView = findViewById<TextView>(R.id.equationView)
        textView.text = equation.stringValue
        updateTextViews()
    }

    private fun updateTextViews() {
        var totalAnswerText = resources.getString(R.string.totalAnswersCount)
        findViewById<TextView>(R.id.totalAnswersCount).text = String.format(totalAnswerText, totalAnswersCount)
        var correctAnswerText = resources.getString(R.string.correctString)
        findViewById<TextView>(R.id.correctAnswers).text = String.format(correctAnswerText, correctAnswersCount)
        var incorrectAnswerText = resources.getString(R.string.incorrectString)
        findViewById<TextView>(R.id.incorrectAnswers).text = String.format(incorrectAnswerText, incorrectAnswersCount)
    }
}