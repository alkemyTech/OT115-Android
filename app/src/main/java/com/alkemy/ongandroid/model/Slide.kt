package com.alkemy.ongandroid.model

import com.google.gson.annotations.SerializedName

data class Slide(
        val name: String,
        val description: String,
        @SerializedName("image")
        val imageUrl: String
)
