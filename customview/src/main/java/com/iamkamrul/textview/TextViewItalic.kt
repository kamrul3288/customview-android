package com.iamkamrul.textview

import android.content.Context
import android.util.AttributeSet
import com.iamkamrul.utils.FontsOverride

open class TextViewItalic(context: Context, attrs: AttributeSet) : TextViewRegular(context, attrs) {
    override fun applyCustomFont() {
        if (FontsOverride.italicFontTypeFace != null){
            typeface = FontsOverride.italicFontTypeFace
        }
    }
}