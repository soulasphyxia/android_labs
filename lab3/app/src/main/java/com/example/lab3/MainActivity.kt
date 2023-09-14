package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    
    private var list = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null) {
            list = ArrayList(savedInstanceState.getIntArray("list")!!.toList())
            if (list != null) {
                for(i in list){
                    addTextView(i)
                }
            }
            var savedYPosition = savedInstanceState.getInt("savedYPosition")
            var scrollView = findViewById<ScrollView>(R.id.scroll_view)
            scrollView.scrollY = savedYPosition;
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
        var randomInt = Random.nextInt(0,100);
        list.add(randomInt)
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
        outState.putIntArray("list", list.toIntArray())
        val scrollY = findViewById<ScrollView>(R.id.scroll_view).scrollY;
        outState.putInt("savedYPosition", scrollY)
    }



}