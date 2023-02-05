package com.iamkamrul.input

import android.util.Patterns
import java.util.regex.Pattern

internal enum class  CountryCode{BD}

internal enum class InputValidationType{
    Phone, Email, Password, EmptyCheck
}

internal enum class PasswordMinLength{
    Five, Six, Eight
}

internal fun  String.isValidEmail():Boolean{
    val emailRegex = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return !(this.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(this).matches())
}