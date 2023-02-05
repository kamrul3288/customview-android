package com.iamkamrul.radio

import android.content.Context
import android.util.AttributeSet
import com.iamkamrul.utils.FontsOverride

open class RadioButtonItalic(context: Context, attrs: AttributeSet) : RadioButtonRegular(context, attrs) {
    override fun applyCustomFont() {
        if (FontsOverride.boldFontTypeFace != null){
            typeface = FontsOverride.italicFontTypeFace
        }
    }
}