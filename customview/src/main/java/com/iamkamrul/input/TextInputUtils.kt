package com.iamkamrul.input

import android.util.Patterns

internal enum class  CountryCode{BD}

internal enum class InputValidationType{
    Phone, Email, Password, EmptyCheck
}

internal enum class PasswordMinLength{
    Five, Six, Eight
}

internal fun  String.isValidEmail():Boolean{
    return !(this.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(this).matches())
}