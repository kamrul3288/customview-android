package com.iamkamrul.utils

import android.content.Context

fun Context.displayRatioValue(value:Int):Int{
    val dpRatio: Float = resources.displayMetrics.density
    return (value * dpRatio).toInt()
}

fun Context.dp(value:Int):Float{
    val dpRatio: Float = resources.displayMetrics.density
    return (value * dpRatio)
}