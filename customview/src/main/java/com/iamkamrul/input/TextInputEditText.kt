package com.iamkamrul.input

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.iamkamrul.utils.FontsOverride

open class TextInputEditText :
    TextInputEditText {

    constructor(context: Context) : super(context){
        applyCustomFont()
    }

    constructor(context: Context, attrs:AttributeSet) : super(context,attrs){
        applyCustomFont()
    }

    constructor(context: Context, attrs:AttributeSet,defStyleAttr:Int) : super(context,attrs,defStyleAttr){
        applyCustomFont()
    }

    private fun applyCustomFont() {
        if (FontsOverride.regularFontTypeFace != null){
            typeface = FontsOverride.regularFontTypeFace
        }
    }

}