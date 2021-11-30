package com.alkemy.ongandroid.model

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var pass: String
)
