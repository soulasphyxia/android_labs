package com.example.lab11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawView: View{
    private var w: Float = 0f
    private var h: Float = 0f
    private var groundY: Float = 0f
    private var treeWidth: Float = 0f
    private var treeHeight: Float = 0f
    private var crownDiameter: Float = 0f
    private var crownX: Float = 0f
    private var crownY: Float = 0f
    private var paint = Paint()
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h = h.toFloat()
        groundY = this.h - this.h / 3
        treeWidth = this.w / 10
        treeHeight = this.h / 6
        crownDiameter = this.w / 5
        crownX = this.w / 2
        crownY = groundY - treeHeight - crownDiameter
    }
    
}