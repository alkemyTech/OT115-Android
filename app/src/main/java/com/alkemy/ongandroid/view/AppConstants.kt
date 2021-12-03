package com.alkemy.ongandroid.view

object AppConstants {


    const val SPECIAL_CHARACTERS_REGEX =
        "?=.*[\\u0020-\\u002F\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E]"
    const val PASSWORD_REGEX = "^" +
            "(?=.*[0-9])" +                 //at least 1 digit
            "(?=.*[a-zA-Z])" +              //any letter
            "($SPECIAL_CHARACTERS_REGEX)" + //at least 1 special character
            ".{4,}\$"                       //at least 4 characters


}