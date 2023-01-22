package com.example.customview

import android.app.Application
import com.iamkamrul.utils.FontsOverride
import com.iamkamrul.utils.FontsOverride.createTypeFace

class ExampleApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        setupFontRes()
    }

    private fun setupFontRes(){
        FontsOverride.regularFontTypeFace = createTypeFace(applicationContext,"fonts/Ubuntu-Regular.ttf")
        FontsOverride.mediumFontTypeFace = createTypeFace(applicationContext,"fonts/Ubuntu-Medium.ttf")
        FontsOverride.semiBoldFontTypeFace = createTypeFace(applicationContext,"fonts/Ubuntu-Bold.ttf")
        FontsOverride.boldFontTypeFace = createTypeFace(applicationContext,"fonts/Ubuntu-Bold.ttf")
        FontsOverride.italicFontTypeFace = createTypeFace(applicationContext,"fonts/Ubuntu-Italic.ttf")
        FontsOverride.setDefaultFont(staticTypefaceFieldName = "MONOSPACE", typeFace = FontsOverride.regularFontTypeFace)
    }
}