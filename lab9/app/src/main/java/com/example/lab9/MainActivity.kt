package com.example.lab9

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity(), OnDataListener {
    private var isTwoPane = false;
    private val handler = RequestsHandler()
    var projectsList : ArrayList<Project> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isTwoPane = findViewById<View>(R.id.frame_left) != null
        if(isTwoPane){
            val searchBtn : View = findViewById(R.id.search_btn);
            val searchField : EditText = findViewById(R.id.search_field)
            searchBtn.setOnClickListener{
                val requestData = searchField.text.toString()
                if(requestData.isNotEmpty()){
                    makeARequest(requestData)
                }else{
                    Log.i("INFOOOO","REQUEST DATA IS NULL")
                }
            }
        }
    }


    private fun makeARequest(requestData: String) = GlobalScope.launch {
        projectsList = handler.getProjectsStartsWith(requestData)
        Log.i("INFOOOO", projectsList[0].fullName)
        MainScope().launch {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_left, LeftFragment(projectsList))
                .add(R.id.frame_right, RightFragment(0))
                .commit()
        }

    }

    override fun onData(Data: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame_right,
                RightFragment(Data)
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }

}