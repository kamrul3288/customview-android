package com.iamkamrul.checkbox

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatCheckBox
import com.iamkamrul.customview.R
import com.iamkamrul.utils.FontsOverride

open class CheckBoxRegular : AppCompatCheckBox{
    private var attrCheckedDrawable: Drawable? = null
    private var attrUnCheckDrawable: Drawable? = null
    @ColorInt private var attrCheckedTextColor:Int = Color.BLACK
    @ColorInt private var attrUnCheckedTextColor:Int = Color.BLACK

    constructor(context: Context) : super(context){
        applyCustomFont()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context,attrs){
        applyCustomFont()
        applyAttributes(attrs,context)
    }

    private fun applyAttributes(attrs: AttributeSet,context: Context){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxRegular, 0, 0)
        attrCheckedDrawable = typedArray.getDrawable(R.styleable.CheckBoxRegular_cb_checked_drawable)
        attrUnCheckDrawable = typedArray.getDrawable(R.styleable.CheckBoxRegular_cb_uncheck_drawable)
        attrCheckedTextColor = typedArray.getColor(R.styleable.CheckBoxRegular_cb_checked_text_color,this.currentTextColor)
        attrUnCheckedTextColor = typedArray.getColor(R.styleable.CheckBoxRegular_cb_uncheck_text_color,this.currentTextColor)
        typedArray.recycle()

        //--------------set text color sate Enable color and disable color-----------------
        setTextColor(
            ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                attrCheckedTextColor,
                attrUnCheckedTextColor,
            )
        ))

        if (attrCheckedDrawable != null && attrUnCheckDrawable != null){
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(android.R.attr.state_checked),attrCheckedDrawable)
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_checked),attrUnCheckDrawable)
            buttonDrawable = stateListDrawable
        }
    }

    open fun applyCustomFont() {
        if (FontsOverride.regularFontTypeFace != null){
            typeface = FontsOverride.regularFontTypeFace
        }
    }
}