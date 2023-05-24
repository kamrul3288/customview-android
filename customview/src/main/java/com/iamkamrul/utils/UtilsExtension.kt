package com.iamkamrul.utils

import android.content.Context

fun Context.displayRatioValue(value:Int):Int{
    val dpRatio: Float = resources.displayMetrics.density
    return (value * dpRatio).toInt()
}

fun Context.dp(value:Float):Float{
    return if (value>0){
        val dpRatio: Float = resources.displayMetrics.density
        (value * dpRatio)
    }else 0f

}