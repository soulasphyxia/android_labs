package com.example.lab4
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var correctAnswersCount: Int = 0
    private var incorrectAnswersCount: Int = 0
    private var totalAnswersCount: Int = 1
    private var correctAnswerString = ""
    private var incorrectAnswerString = ""


    private val randomEquationGenerator = RandomEquationGenerator()
    private val difficultyGenerator = DifficultyGenerator()
    private val randomNumbersGenerator = RandomNumbersGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        correctAnswerString = resources.getString(R.string.correctAnswer)
        incorrectAnswerString = resources.getString(R.string.incorrectAnswer)
        setContentView(R.layout.activity_main)
        initBaseViews()
    }

    private fun initBaseViews() {
        updateTextViews()
        generateTask()
    }

    private fun generateTask() {
        val diff = difficultyGenerator.getDiff(correctAnswersCount, totalAnswersCount)

        val equation : Equation = randomEquationGenerator.generate(diff)
        val equationAnswer = equation.answer

        val randomNumbers : ArrayList<Int> = randomNumbersGenerator.generateNumbersDependsOnTarget(equationAnswer)

        val answers : ArrayList<Int> = ArrayList()
        answers.add(equationAnswer)
        answers.addAll(randomNumbers)

        renderTask(equation, answers.shuffled())
    }

    private fun renderTask(equation: Equation, answers: List<Int>) {

        val listView = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter(this, R.layout.my_list_item, answers)

        listView.adapter = adapter

        listView.onItemClickListener = OnItemClickListener {  _, _, position, _  ->
            totalAnswersCount++
            val selectedAnswer = answers[position]
            val isCorrectAnswer : Boolean  = selectedAnswer == equation.answer
            if(isCorrectAnswer) correctAnswersCount++ else incorrectAnswersCount++
            generateTask()
        }

        val textView = findViewById<TextView>(R.id.equationView)
        textView.text = equation.stringValue
        updateTextViews()
    }

    private fun updateTextViews() {
        val totalAnswerCountText : String = resources.getString(R.string.totalAnswersCount)
        findViewById<TextView>(R.id.totalAnswersCount).text = String.format(totalAnswerCountText,
                                                                            totalAnswersCount)

        val correctAnswersCountText : String = resources.getString(R.string.correctString)
        findViewById<TextView>(R.id.correctAnswers).text = String.format(correctAnswersCountText,
                                                                         correctAnswersCount)

        val incorrectAnswersCountText : String = resources.getString(R.string.incorrectString)
        findViewById<TextView>(R.id.incorrectAnswers).text = String.format(incorrectAnswersCountText,
                                                                           incorrectAnswersCount)
    }
}