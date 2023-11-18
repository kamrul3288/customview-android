package com.iamkamrul.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import com.iamkamrul.customview.R
import com.iamkamrul.utils.dp

class DottedLine (context: Context, attrs: AttributeSet) : CustomView(context, attrs){
    enum class DottedLineOrientation{HORIZONTAL,VERTICAL}

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var attrStrokeWidth = 0f
    private var attrGapSize = context.dp(10f)
    private var attrLineWidth = context.dp(10f)
    private var attrDotBgColor = 0
    private var attrOrientation = DottedLineOrientation.HORIZONTAL

    init {
        applyAttributes(attrs,context)
    }

    private fun applyAttributes(attrs:AttributeSet,context:Context){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DottedLine,0,0)
        attrStrokeWidth = typedArray.getDimension(R.styleable.DottedLine_dot_stroke_width, 2f)
        attrGapSize = typedArray.getDimension(R.styleable.DottedLine_dot_gap_size, 10f)
        attrLineWidth = typedArray.getDimension(R.styleable.DottedLine_dot_line_width, 10f)
        attrDotBgColor = typedArray.getColor(R.styleable.DottedLine_dot_bg_color, Color.BLACK)
        attrOrientation = DottedLineOrientation.values()[typedArray.getInt(R.styleable.DottedLine_dot_orientation, 0)]
        typedArray.recycle()

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = attrStrokeWidth
        paint.color = attrDotBgColor
        paint.pathEffect = DashPathEffect(floatArrayOf(attrLineWidth, attrGapSize), 0f)
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (attrOrientation == DottedLineOrientation.HORIZONTAL){
            val startX = paddingLeft.toFloat()
            val startY = (height / 2).toFloat()
            val endX = width.toFloat() - paddingRight
            canvas.drawLine(startX, startY, endX, startY, paint)
        }else{
            val startX = (width / 2).toFloat()
            val startY = paddingTop.toFloat()
            val endY = height.toFloat() - paddingBottom
            canvas.drawLine(startX, startY, startX, endY, paint)
        }

    }
}
