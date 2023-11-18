package com.iamkamrul.view

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import com.iamkamrul.customview.R

class CustomImageView : AppCompatImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context,attrs){
        applyAttributes(attrs,context)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int) : super(context,attrs,defStyleAttr)

    private fun applyAttributes(attrs: AttributeSet,context: Context){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView,0,0)
        val cornerRadius = typedArray.getDimension(R.styleable.CustomImageView_iv_corner_radius,0f)
        if (cornerRadius>0){
            this.outlineProvider = object : ViewOutlineProvider(){
                override fun getOutline(view: View, outline: Outline?) {
                    outline?.setRoundRect(0,0,view.width,view.height,cornerRadius)
                }
            }
            this.clipToOutline = true
        }
        typedArray.recycle()
    }
}