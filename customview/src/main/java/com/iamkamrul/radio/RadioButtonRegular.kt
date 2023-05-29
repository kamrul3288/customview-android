package com.iamkamrul.radio

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatRadioButton
import com.iamkamrul.customview.R
import com.iamkamrul.utils.FontsOverride
import com.iamkamrul.utils.Shape

open class RadioButtonRegular : AppCompatRadioButton{
    private var attrCheckedBtnDrawable:Drawable? = null
    private var attrUnCheckedBtnDrawable:Drawable? = null
    private var attrCheckedBgDrawable:Drawable? = null
    private var attrUnCheckedBgDrawable:Drawable? = null
    @ColorInt private var attrCheckedTextColor:Int = Color.BLACK
    @ColorInt private var attrUnCheckedTextColor:Int = Color.BLACK

    private var attrCornerRadius = 0f
    private var attrCheckedBgShape:Shape? = null
    private var attrUnCheckedBgShape:Shape? = null
    private var attrCheckedStrokeColor:Int = Color.TRANSPARENT
    private var attrUnCheckedStrokeColor:Int = Color.TRANSPARENT
    private var attrUnCheckedBgColor:Int = Color.TRANSPARENT
    private var attrCheckedBgColor:Int = Color.TRANSPARENT
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
        attrCheckedBtnDrawable = typedArray.getDrawable(R.styleable.RadioButtonRegular_rb_checked_btn_drawable)
        attrUnCheckedBtnDrawable = typedArray.getDrawable(R.styleable.RadioButtonRegular_rb_unchecked_btn_drawable)
        attrCheckedBgDrawable = typedArray.getDrawable(R.styleable.RadioButtonRegular_rb_checked_bg_drawable)
        attrUnCheckedBgDrawable = typedArray.getDrawable(R.styleable.RadioButtonRegular_rb_unchecked_bg_drawable)

        attrCheckedTextColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_checked_text_color,this.currentTextColor)
        attrUnCheckedTextColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_unchecked_text_color,this.currentTextColor)
        attrCornerRadius = typedArray.getDimension(R.styleable.RadioButtonRegular_rb_corner_radius,0f)

        val checkedBgShapeIndex = typedArray.getInt(R.styleable.RadioButtonRegular_rb_checked_bg_shape,-1)
        attrCheckedBgShape = if (checkedBgShapeIndex>=0)Shape.values()[checkedBgShapeIndex] else null
        val unCheckedBgShapeIndex = typedArray.getInt(R.styleable.RadioButtonRegular_rb_unchecked_bg_shape,-1)
        attrUnCheckedBgShape = if (unCheckedBgShapeIndex>=0)Shape.values()[unCheckedBgShapeIndex] else null

        attrCheckedStrokeColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_checked_stroke_color,Color.TRANSPARENT)
        attrStrokeWidth = typedArray.getDimension(R.styleable.RadioButtonRegular_rb_stroke_width,0f)
        attrUnCheckedStrokeColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_unchecked_stroke_color,Color.TRANSPARENT)
        attrCheckedBgColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_checked_bg_color,Color.TRANSPARENT)
        attrUnCheckedBgColor = typedArray.getColor(R.styleable.RadioButtonRegular_rb_unchecked_bg_color,Color.TRANSPARENT)

        val attrBtnStartPadding = typedArray.getDimension(R.styleable.RadioButtonRegular_rb_btn_start_padding,0f)
        typedArray.recycle()

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

        getStateListDrawable(attrCheckedBtnDrawable,attrUnCheckedBtnDrawable)?.let {stateListDrawable ->
            buttonDrawable = if (attrBtnStartPadding>0) InsetDrawable(stateListDrawable, attrBtnStartPadding.toInt(), 0, 0, 0)
            else stateListDrawable
        }
        if (attrCheckedBgDrawable != null && attrUnCheckedBgDrawable != null){
            background = getStateListDrawable(attrCheckedBgDrawable,attrUnCheckedBgDrawable)
        }else if (attrCheckedBgShape != null && attrUnCheckedBgShape!=null){
            val checkedDrawable = getDrawableShape(shape = attrCheckedBgShape!!, strokeColor = attrCheckedStrokeColor, bgColor = attrCheckedBgColor)
            val unCheckedDrawable = getDrawableShape(shape = attrUnCheckedBgShape!!, strokeColor = attrUnCheckedStrokeColor, bgColor = attrUnCheckedBgColor)
            background = getStateListDrawable(checkedDrawable,unCheckedDrawable)
        }
    }


    private fun getDrawableShape(
        shape: Shape,
        strokeColor:Int = Color.TRANSPARENT,
        bgColor:Int = Color.TRANSPARENT
    ):GradientDrawable{
        val drawableBuilder = GradientDrawable()
        drawableBuilder.color = ColorStateList(arrayOf(intArrayOf(android.R.attr.state_enabled)), intArrayOf(bgColor))
        when (shape) {
            // stroke
            Shape.Stroke -> {
                drawableBuilder.shape = GradientDrawable.RECTANGLE
                drawableBuilder.setStroke(attrStrokeWidth.toInt(),strokeColor)
                drawableBuilder.cornerRadius = attrCornerRadius
            }
            // rectangle
            Shape.Rectangle ->{
                drawableBuilder.shape = GradientDrawable.RECTANGLE
                drawableBuilder.cornerRadius = attrCornerRadius
            }
            // oval
            Shape.Oval -> drawableBuilder.shape = GradientDrawable.OVAL

            // stroke Circle
            Shape.StrokeCircle -> {
                drawableBuilder.shape = GradientDrawable.OVAL
                drawableBuilder.setStroke(attrStrokeWidth.toInt(),strokeColor)
            }
        }
        drawableBuilder.invalidateSelf()
        return drawableBuilder
    }

    private fun getStateListDrawable(
        checkedDrawable:Drawable?,
        uncheckedDrawable:Drawable?
    ):StateListDrawable?{
        return if (checkedDrawable != null && uncheckedDrawable != null){
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(android.R.attr.state_checked),checkedDrawable)
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_checked),uncheckedDrawable)
            stateListDrawable
        }else null
    }


    open fun applyCustomFont() {
        if (FontsOverride.regularFontTypeFace != null){
            typeface = FontsOverride.regularFontTypeFace
        }
    }

}