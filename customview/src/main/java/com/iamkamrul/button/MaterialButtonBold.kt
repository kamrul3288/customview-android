package com.iamkamrul.button

import android.content.Context
import android.util.AttributeSet
import com.iamkamrul.utils.FontsOverride

class MaterialButtonBold(context: Context, attrs: AttributeSet) : MaterialButtonRegular(context, attrs) {
    override fun applyCustomFont() {
        if (FontsOverride.boldFontTypeFace != null){
            typeface = FontsOverride.boldFontTypeFace
        }
    }
}