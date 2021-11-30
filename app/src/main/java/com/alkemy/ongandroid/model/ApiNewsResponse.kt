package com.alkemy.ongandroid.model

data class ApiNewsResponse(
    val success:Boolean = false,
    val data: List<ApiData> = listOf(),
    val message: String = ""
)

data class ApiData(
    val id: Int = 0,
    val name: String = "",
    val content: String = "",
    val image:String = "",
    val user_id: Int? = null,
    val category_id: Int = 0,
    val created_at: String = "",
    val updated_at: String = "",
    val deleted_at: String? = null
)