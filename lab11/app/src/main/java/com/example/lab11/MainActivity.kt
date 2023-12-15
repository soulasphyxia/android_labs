package com.example.lab11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Math.PI
import java.lang.Math.cos
import java.lang.Math.pow
import java.lang.Math.sin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class DrawSurface : SurfaceView, SurfaceHolder.Callback {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private var radius: Float = 50f
    private var cx = 0.0f
    private var cy = 0.0f
    private var xt = 2.0f
    private var yt = 2.0f
    private var paint = Paint()
    private lateinit var job: Job

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {
    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        paint.color = Color.WHITE
        cx = (width /2).toFloat()
        cy = (height /2).toFloat()
        job = GlobalScope.launch {
            var canvas: Canvas?
            while (true) {
                canvas = holder.lockCanvas(null)
                if (canvas != null) {
                    canvas.drawColor(Color.argb(200, 0, 0, 0))
                    canvas.drawCircle(cx, cy, radius, paint)
                    holder.unlockCanvasAndPost(canvas)
                }

                cx += 5 * xt
                cy += 5 * yt

                if(cx + radius > width || cx + radius < 0){
                    xt += 1f
                    xt = -xt
                }

                if(cy + radius > height || cy + radius < 0){
                    yt = -yt
                }

            }
        }

    }
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        job.cancel()
    }

    init {
        holder.addCallback(this)
    }
}