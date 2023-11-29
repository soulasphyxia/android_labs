package com.example.lab8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.lab8.fragments.LeftFragment
import com.example.lab8.fragments.RightFragment
import com.example.lab8.generators.DifficultyGenerator

class MainActivity : AppCompatActivity(), OnDataListener {
    private var isTwoPane = false;
    private var difficulty = 1;

    private var correctAnswersCount: Int = 0
    private var incorrectAnswersCount: Int = 0
    private var totalAnswersCount: Int = 0

    private val difficultyGenerator = DifficultyGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isTwoPane = findViewById<View>(R.id.frame_left) != null
        if (isTwoPane) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_left, LeftFragment())
                .add(R.id.frame_right, RightFragment(0))
                .commit()
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

    override fun onData(Data: Int) {
        difficulty = difficultyGenerator.getDiff(correctAnswersCount,totalAnswersCount)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame_right,
                RightFragment(Data,difficulty)
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }

    fun updateTextViews(isCorrect: Boolean) {
        totalAnswersCount++
        if(isCorrect) correctAnswersCount++ else incorrectAnswersCount++

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