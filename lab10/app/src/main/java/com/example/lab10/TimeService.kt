package com.example.lab10

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimeService: Service() {
    private var counter = 0
    private lateinit var job: Job
    private val myBinder = MyBinder()

    private var delayMs : Long = 1000

    override fun onBind(intent: Intent?): IBinder? {
        if (intent != null) {
            counter = intent.getIntExtra("counter",0)
            delayMs = intent.getIntExtra("delayMs",1000).toLong()
        }
        return myBinder;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job = GlobalScope.launch {
            while (true) {
                delay(delayMs)
                Log.d("SERVICE", "Timer Is Ticking: " + counter)
                counter++
                val intent = Intent(BROADCAST_TIME_EVENT);
                intent.putExtra("counter", counter);
                sendBroadcast(intent);
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("SERVICE", "onDestroy")
        job.cancel()
        super.onDestroy()
    }

    fun getCounter(): Int {
        return counter
    }

    inner class MyBinder : Binder() {
        fun getService() : TimeService {
            return this@TimeService
        }
    }
}