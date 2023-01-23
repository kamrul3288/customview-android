package com.iamkamrul.button

import android.content.Context
import android.util.AttributeSet
import com.iamkamrul.utils.FontsOverride

class ButtonItalic(context: Context, attrs: AttributeSet) : ButtonRegular(context, attrs) {
    override fun applyCustomFont() {
        if (FontsOverride.boldFontTypeFace != null){
            typeface = FontsOverride.italicFontTypeFace
        }
    }
}