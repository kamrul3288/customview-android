package com.iamkamrul.layout

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import com.iamkamrul.customview.R
import com.iamkamrul.utils.GradientOrientation
import com.iamkamrul.utils.Shape
import com.iamkamrul.utils.dp

class CustomConstraintLayout : ConstraintLayout {

    private  var drawableBuilder: GradientDrawable? = null
    private var attrCornerRadius = 0f
    private var attrTopLeftCornerRadius = 0f
    private var attrTopRightCornerRadius = 0f
    private var attrBottomLeftCornerRadius = 0f
    private var attrBottomRightCornerRadius = 0f
    private var attrShapeType = Shape.Rectangle
    private var attrStrokeColor = Color.TRANSPARENT
    private var attrStrokeWidth = 0f
    private var attrBgDisableColor = Color.GRAY
    private var attrGradientOrientation = GradientOrientation.TOP_BOTTOM
    private var attrGradientTopColor:Int = -1
    private var attrGradientMiddleColor:Int = -1
    private var attrGradientBottomColor:Int = -1
    private var attrRippleColor:Int = Color.TRANSPARENT

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs:AttributeSet) : super(context,attrs){
        applyAttributes(attrs,context)
    }
    constructor(context: Context, attrs:AttributeSet,defStyleAttr:Int) : super(context,attrs,defStyleAttr)

    @SuppressLint("CustomViewStyleable")
    private fun applyAttributes(attrs: AttributeSet,context: Context){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomConstraintLayout,0,0)
        attrCornerRadius = typedArray.getDimension(R.styleable.CustomConstraintLayout_cl_corner_radius,0f)
        attrTopLeftCornerRadius = typedArray.getDimension(R.styleable.CustomConstraintLayout_cl_topLeft_corner_radius,0f)
        attrTopRightCornerRadius = typedArray.getDimension(R.styleable.CustomConstraintLayout_cl_topRight_corner_radius,0f)
        attrBottomLeftCornerRadius = typedArray.getDimension(R.styleable.CustomConstraintLayout_cl_bottomLeft_corner_radius,0f)
        attrBottomRightCornerRadius = typedArray.getDimension(R.styleable.CustomConstraintLayout_cl_bottomRight_corner_radius,0f)
        val attrShapeTypeInt = typedArray.getInt(R.styleable.CustomConstraintLayout_cl_background_shape,-1)
        attrStrokeColor = typedArray.getColor(R.styleable.CustomConstraintLayout_cl_stroke_color,Color.TRANSPARENT)
        attrStrokeWidth = typedArray.getDimension(R.styleable.CustomConstraintLayout_cl_stroke_width,0f)
        attrBgDisableColor = typedArray.getColor(R.styleable.CustomConstraintLayout_cl_disable_bg_color, Color.GRAY)
        attrGradientOrientation = GradientOrientation.values()[typedArray.getInt(R.styleable.CustomConstraintLayout_cl_gradient_orientation, 0)]
        attrGradientTopColor = typedArray.getColor(R.styleable.CustomConstraintLayout_cl_gradient_top_color,-1)
        attrGradientMiddleColor = typedArray.getColor(R.styleable.CustomConstraintLayout_cl_gradient_middle_color,-1)
        attrGradientBottomColor = typedArray.getColor(R.styleable.CustomConstraintLayout_cl_gradient_bottom_color,-1)
        attrRippleColor = typedArray.getColor(R.styleable.CustomConstraintLayout_cl_ripple_color,Color.TRANSPARENT)
        typedArray.recycle()

        //--------------check user select any shape type or not-----------------
        if (attrShapeTypeInt != -1){
            attrShapeType =  Shape.values()[attrShapeTypeInt]

            //--------------check which drawable is are-----------------
            when (background) {
                is GradientDrawable -> {
                    drawableBuilder = background as GradientDrawable
                    //--------disable color only support Android N or above
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        drawableBuilder?.color = getBgColorStateList(bgColor = drawableBuilder?.color?.getColorForState(intArrayOf(android.R.attr.state_enabled),Color.GRAY)?:Color.GRAY,)
                    }
                    applyShape()
                }


                is ColorDrawable -> {
                    val colorDrawable = background as ColorDrawable
                    drawableBuilder = GradientDrawable()
                    drawableBuilder?.color = getBgColorStateList(bgColor = colorDrawable.color)
                    applyShape()
                }

                else ->{
                    drawableBuilder = GradientDrawable()
                    //----------- check user is request for any gradient background color or not------------------
                    if (attrGradientTopColor != -1){
                        drawableBuilder?.color = getBgColorStateList()
                        //----------- set gradient color bg------------------
                        drawableBuilder?.colors = intArrayOf(
                            attrGradientTopColor,
                            if (attrGradientMiddleColor == -1)attrGradientTopColor else attrGradientMiddleColor,
                            if (attrGradientBottomColor == -1)attrGradientTopColor else attrGradientBottomColor
                        )
                        drawableBuilder?.orientation = getDrawableOrientation()
                    }
                    applyShape()
                }
            }
        }
    }
    private fun applyShape(){
        when (attrShapeType) {
            // stroke
            Shape.Stroke -> {
                drawableBuilder?.shape = GradientDrawable.RECTANGLE
                drawableBuilder?.setStroke(attrStrokeWidth.toInt(),attrStrokeColor)
                if (attrCornerRadius>0){
                    drawableBuilder?.cornerRadius = attrCornerRadius
                }else if (attrTopLeftCornerRadius>0 || attrTopRightCornerRadius>0 || attrBottomRightCornerRadius>0 || attrBottomLeftCornerRadius>0){
                    drawableBuilder?.cornerRadii = floatArrayOf(attrTopLeftCornerRadius,attrTopLeftCornerRadius,attrTopRightCornerRadius,attrTopRightCornerRadius,attrBottomRightCornerRadius,attrBottomRightCornerRadius,attrBottomLeftCornerRadius,attrBottomLeftCornerRadius)
                }
            }
            // rectangle
            Shape.Rectangle ->{
                drawableBuilder?.shape = GradientDrawable.RECTANGLE
                if (attrCornerRadius>0){
                    drawableBuilder?.cornerRadius = attrCornerRadius
                }else if (attrTopLeftCornerRadius>0 || attrTopRightCornerRadius>0 || attrBottomRightCornerRadius>0 || attrBottomLeftCornerRadius>0){
                    drawableBuilder?.cornerRadii = floatArrayOf(attrTopLeftCornerRadius,attrTopLeftCornerRadius,attrTopRightCornerRadius,attrTopRightCornerRadius,attrBottomRightCornerRadius,attrBottomRightCornerRadius,attrBottomLeftCornerRadius,attrBottomLeftCornerRadius)
                }
            }
            // oval
            Shape.Oval -> drawableBuilder?.shape = GradientDrawable.OVAL

            // stroke Circle
            Shape.StrokeCircle -> {
                drawableBuilder?.shape = GradientDrawable.OVAL
                drawableBuilder?.setStroke(attrStrokeWidth.toInt(),attrStrokeColor)
            }
        }

        //---------create ripple color-----------------
        if (attrRippleColor != Color.TRANSPARENT){
            val rippleDrawable = RippleDrawable(
                ColorStateList(
                    arrayOf(intArrayOf(android.R.attr.state_pressed)),
                    intArrayOf(attrRippleColor)
                ),
                drawableBuilder,
                null
            )
            background = rippleDrawable
            rippleDrawable.invalidateSelf()
        }else{
            background = drawableBuilder
        }
        drawableBuilder?.invalidateSelf()
    }



    open fun setShape(
        @ColorInt backgroundColor:Int = Color.TRANSPARENT,
        shapeType:Shape,
        @Dimension cornerRadius:Float = 0f,
        @ColorInt strokeColor:Int = Color.TRANSPARENT,
        @Dimension strokeWidth:Float = 0f,
        @Dimension topLeftCornerRadius:Float = 0f,
        @Dimension topRightCornerRadius:Float = 0f,
        @Dimension bottomLeftCornerRadius:Float = 0f,
        @Dimension bottomRightCornerRadius:Float = 0f,
    ){
        drawableBuilder = null
        drawableBuilder = GradientDrawable()
        drawableBuilder?.color = getBgColorStateList(bgColor = backgroundColor)

        attrShapeType = shapeType
        attrCornerRadius = context.dp(cornerRadius)
        attrStrokeColor = strokeColor
        attrStrokeWidth = strokeWidth
        attrTopLeftCornerRadius = context.dp(topLeftCornerRadius)
        attrTopRightCornerRadius = context.dp(topRightCornerRadius)
        attrBottomLeftCornerRadius = context.dp(bottomLeftCornerRadius)
        attrBottomRightCornerRadius = context.dp(bottomRightCornerRadius)
        applyShape()
    }


    private fun getBgColorStateList(bgColor:Int? = null):ColorStateList{
        //------ if the background color is null then put only disable color---------
        val colorStatePair = if (bgColor == null)
            Pair(
                first = arrayOf(intArrayOf(-android.R.attr.state_enabled)),
                second = intArrayOf(attrBgDisableColor)
            )
        else Pair(
            first = arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_enabled)
            ),
            second = intArrayOf(bgColor,attrBgDisableColor))

        return ColorStateList(
            colorStatePair.first,
            colorStatePair.second
        )
    }

    private fun getDrawableOrientation():GradientDrawable.Orientation{
        return when(attrGradientOrientation){
            GradientOrientation.TOP_BOTTOM -> GradientDrawable.Orientation.TOP_BOTTOM
            GradientOrientation.BL_TR -> GradientDrawable.Orientation.BL_TR
            GradientOrientation.BOTTOM_TOP -> GradientDrawable.Orientation.BOTTOM_TOP
            GradientOrientation.BR_TL -> GradientDrawable.Orientation.BR_TL
            GradientOrientation.LEFT_RIGHT -> GradientDrawable.Orientation.LEFT_RIGHT
            GradientOrientation.RIGHT_LEFT -> GradientDrawable.Orientation.RIGHT_LEFT
            GradientOrientation.TL_BR -> GradientDrawable.Orientation.TL_BR
            GradientOrientation.TR_BL -> GradientDrawable.Orientation.TR_BL
        }
    }

}
