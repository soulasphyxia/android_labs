package com.example.lab3
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    
    private var numbersList : ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null) {

            numbersList = ArrayList(savedInstanceState.getIntArray("savedNumbersList")!!.toList())
            for(i in numbersList){
                addTextView(i)
            }

            val savedYScrollPosition = savedInstanceState.getInt("savedYScrollPosition")
            val scrollView = findViewById<ScrollView>(R.id.scroll_view)
            scrollView.scrollY = savedYScrollPosition
        }
        Log.i("MyInfo", "Метод onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("MyInfo", "Метод onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MyInfo", "Метод onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MyInfo", "Метод onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MyInfo", "Метод onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MyInfo", "Метод onDestroy")
    }

    fun buttonAddClick(view: View) {
        val randomInt : Int = Random.nextInt(0,100)
        numbersList.add(randomInt)
        addTextView(randomInt)
    }

    private fun addTextView(num: Int) {
        val textView = TextView(this)
        textView.text = num.toString()
        textView.textSize = 24f
        val container = findViewById<LinearLayout>(R.id.innerContainer)
        container.addView(textView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("savedNumbersList", numbersList.toIntArray())
        val currentScrollYPosition : Int = findViewById<ScrollView>(R.id.scroll_view).scrollY
        outState.putInt("savedYScrollPosition", currentScrollYPosition)
    }

}