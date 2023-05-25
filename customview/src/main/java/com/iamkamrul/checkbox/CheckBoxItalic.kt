package com.iamkamrul.checkbox

import android.content.Context
import android.util.AttributeSet
import com.iamkamrul.utils.FontsOverride

class CheckBoxItalic(context: Context, attrs: AttributeSet) : CheckBoxRegular(context, attrs) {
    override fun applyCustomFont() {
        if (FontsOverride.boldFontTypeFace != null){
            typeface = FontsOverride.italicFontTypeFace
        }
    }
}