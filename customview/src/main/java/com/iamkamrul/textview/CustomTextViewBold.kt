package com.iamkamrul.textview

import android.content.Context
import android.util.AttributeSet
import com.iamkamrul.utils.FontsOverride

open class CustomTextViewBold(context: Context, attrs: AttributeSet) : CustomTextView(context, attrs) {
    override fun applyCustomFont() {
        if (FontsOverride.boldFontTypeFace != null){
            typeface = FontsOverride.boldFontTypeFace
        }
    }
}