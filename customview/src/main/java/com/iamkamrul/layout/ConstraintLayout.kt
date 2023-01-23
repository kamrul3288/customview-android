package com.iamkamrul.layout

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.iamkamrul.R

open class ConstraintLayout : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs:AttributeSet) : super(context,attrs){
        applyAttributes(attrs,context)
    }

    constructor(context: Context, attrs:AttributeSet,defStyleAttr:Int) : super(context,attrs,defStyleAttr)

    @SuppressLint("CustomViewStyleable")
    private fun applyAttributes(attrs: AttributeSet,context: Context){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ConstraintLayout,0,0)
        val backgroundColor = typedArray.getColor(R.styleable.ConstraintLayout_cl_background_color, Color.TRANSPARENT)
        val backgroundBorderRadius = typedArray.getDimension(R.styleable.ConstraintLayout_cl_border_radius,0f)
        val backgroundShapeType = typedArray.getString(R.styleable.ConstraintLayout_cl_background_shape)?:"1" //1(stroke) 2(rectangle)
        val strokeColor = typedArray.getColor(R.styleable.ConstraintLayout_cl_stroke_color, Color.TRANSPARENT)
        val strokeWithSize = typedArray.getDimension(R.styleable.ConstraintLayout_cl_stroke_width,0f)
        val backgroundRippleColor = typedArray.getColor(R.styleable.ConstraintLayout_cl_ripple_color, Color.LTGRAY)
        val backgroundDisableColor = typedArray.getColor(R.styleable.ConstraintLayout_cl_disable_color, Color.GRAY)
        typedArray.recycle()


        val drawableBuilder = GradientDrawable()
        val contentColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_activated),
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_enabled)
            ),
            intArrayOf(
                backgroundColor,
                backgroundColor,
                backgroundDisableColor
            )
        )
        drawableBuilder.color = contentColor

        when (backgroundShapeType) {
            // stroke
            "1","0x1" -> {
                drawableBuilder.shape = GradientDrawable.RECTANGLE
                drawableBuilder.setStroke(strokeWithSize.toInt(),strokeColor)
                drawableBuilder.cornerRadius = backgroundBorderRadius
            }
            // rectangle
            "2","0x2" ->{
                drawableBuilder.shape = GradientDrawable.RECTANGLE
                drawableBuilder.cornerRadius = backgroundBorderRadius
            }
            // oval
            "3","0x3" -> drawableBuilder.shape = GradientDrawable.OVAL

            // stroke Circle
            "4","0x4" -> {
                drawableBuilder.shape = GradientDrawable.OVAL
                drawableBuilder.setStroke(strokeWithSize.toInt(),strokeColor)
            }
        }


        val rippleDrawable = RippleDrawable(
            ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_focused),
                    intArrayOf(android.R.attr.state_activated)
                ),
                intArrayOf(
                    backgroundRippleColor,
                    backgroundRippleColor,
                    backgroundRippleColor
                )
            ),
            drawableBuilder,
            null
        )
        drawableBuilder.invalidateSelf()
        rippleDrawable.invalidateSelf()
        background = rippleDrawable


    }

}
