package com.iamkamrul.utils

import android.content.Context
import android.graphics.Typeface

object FontsOverride {
    var regularFontTypeFace:Typeface? = null
    var boldFontTypeFace:Typeface? = null
    var semiBoldFontTypeFace:Typeface? = null
    var mediumFontTypeFace:Typeface? = null
    var italicFontTypeFace:Typeface? = null

    fun setDefaultFont(staticTypefaceFieldName: String, typeFace: Typeface?) {
        typeFace?.let {
            replaceFont(staticTypefaceFieldName, typeFace)
        }
    }


    private fun replaceFont(staticTypefaceFieldName: String, newTypeface: Typeface) {
        try {
            val staticField = Typeface::class.java.getDeclaredField(staticTypefaceFieldName)
            staticField.isAccessible = true
            staticField.set(null, newTypeface)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun createTypeFace(context: Context,fontName:String):Typeface{
        return Typeface.createFromAsset(context.assets,fontName)
    }
}