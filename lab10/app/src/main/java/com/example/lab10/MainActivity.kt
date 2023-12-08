package com.example.lab10

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
const val BROADCAST_TIME_EVENT = "com.example.lab09.timeevent"
class MainActivity : AppCompatActivity() {
    var myService: TimeService? = null
    var isBound = false
    val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimeService.MyBinder
            myService = binder.getService()
            isBound = true
        }
        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }
    private var counterInitValue = 1
    private var delayMs = 1000

    private var counter = 0

    var receiver: BroadcastReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                counter = intent.getIntExtra("counter", 0)
                val textCounter = findViewById<TextView>(R.id.textCounter)
                textCounter.text = counter.toString()
            }
        }

        val filter = IntentFilter(BROADCAST_TIME_EVENT)

        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy()
    }

    fun buttonStartService(view: View) {
        val intent = Intent(this, TimeService::class.java)
        setInitialsValues()
        intent.putExtra("delayMs",delayMs)
        intent.putExtra("counter",counter)
        startService(intent)
        bindService(intent,
            myConnection, Context.BIND_AUTO_CREATE)

    }
    fun buttonStopService(view: View) {
        stopService(Intent(this, TimeService::class.java))
        unbindService(myConnection)
    }

    private fun setInitialsValues(){
        counterInitValue = findViewById<EditText>(R.id.initValueEditText).text.toString().toInt()
        delayMs = findViewById<EditText>(R.id.delayEditText).text.toString().toInt()
        counter = counterInitValue
    }

    fun resetService(view: View){
        counter = counterInitValue
        buttonStopService(view)
        buttonStartService(view)
    }


}