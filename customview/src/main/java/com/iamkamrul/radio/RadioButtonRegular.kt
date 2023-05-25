package com.iamkamrul.radio

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatRadioButton
import com.iamkamrul.customview.R
import com.iamkamrul.utils.FontsOverride
import com.iamkamrul.utils.Shape

open class RadioButtonRegular : AppCompatRadioButton{
    private  var drawableBuilder: GradientDrawable? = null
    private var attrCheckedDrawable:Drawable? = null
    private var attrUnCheckDrawable:Drawable? = null
    @ColorInt private var attrCheckedTextColor:Int = Color.BLACK
    @ColorInt private var attrUnCheckedTextColor:Int = Color.BLACK
    private var attrCornerRadius = 0f
    private var attrShapeType = Shape.Rectangle
    private var attrStrokeColor = Color.TRANSPARENT
    private var attrStrokeWidth = 0f

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
        attrCheckedDrawable = typedArray.getDrawable(R.styleable.RadioButtonRegular_rb_checked_drawable)
        attrUnCheckDrawable = typedArray.getDrawable(R.styleable.RadioButtonRegular_rb_uncheck_drawable)
        attrCheckedTextColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_checked_text_color,this.currentTextColor)
        attrUnCheckedTextColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_uncheck_text_color,this.currentTextColor)
        attrCornerRadius = typedArray.getDimension(R.styleable.RadioButtonRegular_rb_corner_radius,0f)
        val attrShapeTypeInt = typedArray.getInt(R.styleable.RadioButtonRegular_rb_background_shape,-1)
        attrStrokeColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_stroke_color,Color.TRANSPARENT)
        attrStrokeWidth = typedArray.getDimension(R.styleable.RadioButtonRegular_rb_stroke_width,0f)
        typedArray.recycle()

        //--------- check shape type---------------
        if (attrShapeTypeInt != -1){
            attrShapeType =  Shape.values()[attrShapeTypeInt]
        }

        //--------------set text color sate Enable color and disable color-----------------
        setTextColor(ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                attrCheckedTextColor,
                attrUnCheckedTextColor,
            )
        ))

        when (background) {
            is GradientDrawable -> {
                Toast.makeText(context, "GradientDrawable", Toast.LENGTH_SHORT).show()
                drawableBuilder = background as GradientDrawable
                //--------disable color only support Android N or above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    drawableBuilder?.color = getBgColorStateList(bgColor = drawableBuilder?.color?.getColorForState(intArrayOf(android.R.attr.state_enabled),Color.GRAY)?:Color.GRAY,)
                }
                applyShape()
            }

            is ColorDrawable -> {
                Toast.makeText(context, "ColorDrawable", Toast.LENGTH_SHORT).show()
                val colorDrawable = background as ColorDrawable
                drawableBuilder = GradientDrawable()
                drawableBuilder?.color = getBgColorStateList(bgColor = colorDrawable.color)
                applyShape()
            }
            else ->{
                if (attrCheckedDrawable != null && attrUnCheckDrawable != null){
                    val stateListDrawable = StateListDrawable()
                    stateListDrawable.addState(intArrayOf(android.R.attr.state_checked),attrCheckedDrawable)
                    stateListDrawable.addState(intArrayOf(-android.R.attr.state_checked),attrUnCheckDrawable)
                    buttonDrawable = stateListDrawable
                }
            }
        }
    }

    private fun applyShape(){
        when (attrShapeType) {
            // stroke
            Shape.Stroke -> {
                drawableBuilder?.shape = GradientDrawable.RECTANGLE
                drawableBuilder?.setStroke(attrStrokeWidth.toInt(),attrStrokeColor)
                drawableBuilder?.cornerRadius = attrCornerRadius
            }
            // rectangle
            Shape.Rectangle ->{
                drawableBuilder?.shape = GradientDrawable.RECTANGLE
                drawableBuilder?.cornerRadius = attrCornerRadius
            }
            // oval
            Shape.Oval -> drawableBuilder?.shape = GradientDrawable.OVAL

            // stroke Circle
            Shape.StrokeCircle -> {
                drawableBuilder?.shape = GradientDrawable.OVAL
                drawableBuilder?.setStroke(attrStrokeWidth.toInt(),attrStrokeColor)
            }
        }

        background = drawableBuilder
        drawableBuilder?.invalidateSelf()
    }


    private fun getBgColorStateList(bgColor:Int):ColorStateList?{
        if (attrUnCheckDrawable !is ColorDrawable) return null

        return ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(bgColor,(attrUnCheckDrawable as ColorDrawable).color)
        )
    }

    open fun applyCustomFont() {
        if (FontsOverride.regularFontTypeFace != null){
            typeface = FontsOverride.regularFontTypeFace
        }
    }

}