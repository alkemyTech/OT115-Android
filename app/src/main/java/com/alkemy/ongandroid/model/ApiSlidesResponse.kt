package com.alkemy.ongandroid.model

import com.google.gson.annotations.SerializedName

data class ApiSlidesResponse(
        val success: Boolean,
        @SerializedName("data")
        val slideList: List<Slide>
)
