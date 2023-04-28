package com.iamkamrul.radio

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.appcompat.widget.AppCompatRadioButton
import com.iamkamrul.customview.R
import com.iamkamrul.utils.FontsOverride
import com.iamkamrul.utils.Shape

open class RadioButtonRegular : AppCompatRadioButton{

    constructor(context: Context) : super(context){
        applyCustomFont()
    }

    constructor(context: Context, attrs:AttributeSet) : super(context,attrs){
        applyCustomFont()
        applyAttributes(attrs,context)
    }

    constructor(context: Context, attrs:AttributeSet,defStyleAttr:Int) : super(context,attrs,defStyleAttr){
        applyCustomFont()
    }

    @SuppressLint("CustomViewStyleable")
    private fun applyAttributes(attrs: AttributeSet,context: Context) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadioButtonRegular, 0, 0)
        val backgroundColor =
            typedArray.getColor(R.styleable.RadioButtonRegular_rb_bg_color, Color.TRANSPARENT)
        val selectedBackgroundColor = typedArray.getColor(
            R.styleable.RadioButtonRegular_rb_selected_bg_color,
            Color.TRANSPARENT
        )
        val backgroundBorderRadius = typedArray.getDimension(R.styleable.RadioButtonRegular_rb_border_radius, 0f)
        val backgroundShapeType = Shape.values()[typedArray.getInt(R.styleable.RadioButtonRegular_rb_background_shape, 1)]
        val strokeColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_stroke_color, Color.TRANSPARENT)
        val strokeWithSize = typedArray.getDimension(R.styleable.RadioButtonRegular_rb_stroke_width, 0f)
        val backgroundRippleColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_ripple_color, Color.LTGRAY)
        val backgroundDisableColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_disable_color, Color.GRAY)
        val textColor: Int = typedArray.getColor(R.styleable.RadioButtonRegular_rb_text_color, Color.BLACK)
        val selectedTextColor: Int = typedArray.getColor(R.styleable.RadioButtonRegular_rb_selected_text_color, Color.BLACK)
        val selectedStrokeColor: Int = typedArray.getColor(R.styleable.RadioButtonRegular_rb_selected_stroke_color, Color.TRANSPARENT)
        typedArray.recycle()


        setTextColor(
            ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_activated),
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_enabled),
                    intArrayOf(-android.R.attr.state_enabled)
                ),
                intArrayOf(
                    textColor,
                    selectedTextColor,
                    textColor,
                    textColor
                )
            )
        )

        val drawableBuilder = GradientDrawable()
        val contentColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_activated),
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_enabled)
            ),
            intArrayOf(
                backgroundColor,
                selectedBackgroundColor,
                backgroundColor,
                backgroundDisableColor
            )
        )
        drawableBuilder.color = contentColor

        when (backgroundShapeType) {
            // stroke
            Shape.Stroke -> {
                drawableBuilder.shape = GradientDrawable.RECTANGLE
                drawableBuilder.setStroke(
                    strokeWithSize.toInt(), ColorStateList(
                        arrayOf(
                            intArrayOf(android.R.attr.state_activated),
                            intArrayOf(android.R.attr.state_checked),
                            intArrayOf(android.R.attr.state_enabled),
                            intArrayOf(-android.R.attr.state_enabled)
                        ),
                        intArrayOf(
                            strokeColor,
                            selectedStrokeColor,
                            strokeColor,
                            strokeColor
                        )
                    )
                )
                drawableBuilder.cornerRadius = backgroundBorderRadius
            }
            // rectangle
            Shape.Rectangle -> {
                drawableBuilder.shape = GradientDrawable.RECTANGLE
                drawableBuilder.cornerRadius = backgroundBorderRadius
            }
            // oval
            Shape.Oval -> drawableBuilder.shape = GradientDrawable.OVAL

            // stroke Circle
            Shape.StrokeCircle -> {
                drawableBuilder.shape = GradientDrawable.OVAL
                drawableBuilder.setStroke(strokeWithSize.toInt(), strokeColor)
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

    open fun applyCustomFont() {
        if (FontsOverride.regularFontTypeFace != null){
            typeface = FontsOverride.regularFontTypeFace
        }
    }

}