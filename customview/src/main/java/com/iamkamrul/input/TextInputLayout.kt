package com.iamkamrul.input

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.iamkamrul.R
import com.iamkamrul.textview.TextViewRegular
import com.iamkamrul.utils.FontsOverride
import com.iamkamrul.utils.displayRatioValue

class TextInputLayout : TextInputLayout {
    private lateinit var errorTextView: TextView
    private lateinit var errorView: LinearLayout
    private  var errorIcon:Drawable? = null

    constructor(context: Context) : super(context){
        applyCustomFont()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context,attrs){
        applyCustomFont()
        applyAttribute(attrs,context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int) : super(context,attrs,defStyleAttr){
        applyCustomFont()
    }

    private fun applyCustomFont() {
        if (FontsOverride.regularFontTypeFace != null){
            typeface = FontsOverride.regularFontTypeFace
        }
    }


    @SuppressLint("CustomViewStyleable")
    private fun applyAttribute(attrs: AttributeSet, context: Context){

        //---------------validation-------------------------
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.TextInputLayout,0,0)
        val isValidationEnable = typedArray.getBoolean(R.styleable.TextInputLayout_input_layout_validation_enable,false)
        val validationType = typedArray.getString(R.styleable.TextInputLayout_input_layout_validation_type)
        val errorMessage = typedArray.getString(R.styleable.TextInputLayout_input_layout_error_message)
        errorIcon = typedArray.getDrawable(R.styleable.TextInputLayout_input_layout_error_icon)
        val errorTextColor = typedArray.getColor(R.styleable.TextInputLayout_input_layout_error_text_color,Color.RED)
        val errorTextSize = typedArray.getDimension(R.styleable.TextInputLayout_input_layout_error_text_size,12f)

        errorTextView = TextViewRegular(context)
        errorTextView.setTextColor(errorTextColor)
        errorTextView.textSize = errorTextSize
        val errorImageView = ImageView(context)

        //------------------------create error view----------------------
        errorView = LinearLayout(context)
        errorView.orientation = HORIZONTAL
        errorView.addView(errorImageView)
        errorView.addView(errorTextView)
        errorView.gravity = Gravity.CENTER_VERTICAL
        addView(errorView)
        errorView.isVisible = false

        //----------------error image margin------------------
        val errorImageViewParams = errorImageView.layoutParams
        errorImageViewParams.width = context.displayRatioValue(20)
        errorImageViewParams.height = context.displayRatioValue(20)
        (errorImageViewParams as MarginLayoutParams)
        errorImageViewParams.rightMargin = context.displayRatioValue(4)
        errorImageView.layoutParams = errorImageViewParams

        //------------- error view margin----------------------
        val errorViewParams = errorView.layoutParams
        (errorViewParams as MarginLayoutParams)
        errorViewParams.topMargin = context.displayRatioValue(4)
        errorView.layoutParams = errorViewParams



        if (errorIcon != null && !errorMessage.isNullOrEmpty()){
            errorImageView.setImageDrawable(errorIcon)
            setErrorTextAppearance(R.style.ErrorTextAppearance)
            errorIconDrawable = null
        }

        if (isValidationEnable){
            addOnEditTextAttachedListener {
                editText?.doAfterTextChanged {
                    it?.let {

                        //------------phone number validation-----------------
                        if (validationType == "0x1" || validationType == "1"){
                            if (it.length<10) setErrorText(errorMessage)
                            else setErrorText(null)
                            return@let
                        }

                        //------------email number validation-----------------
                        if (validationType == "0x2" || validationType == "2"){//
                            if (it.length<10) setErrorText(errorMessage)
                            else setErrorText(null)
                            return@let
                        }

                        //------------password validation-----------------
                        if (validationType == "0x3" || validationType == "3"){
                            if (it.length<6) setErrorText(errorMessage)
                            else setErrorText(null)
                            return@let
                        }

                        //------------empty validation-----------------
                        if (validationType == "0x4" || validationType == "4"){
                            if (it.isEmpty()) setErrorText(errorMessage)
                            else setErrorText(null)
                            return@let
                        }
                    }
                }
            }
        }
        typedArray.recycle()
    }

    fun setErrorText(text:String?){
        this.isErrorEnabled = text?.isNotEmpty() == true
        errorView.isVisible = text?.isNotEmpty() == true && errorIcon != null
        error = text
        errorTextView.text = text
    }
}