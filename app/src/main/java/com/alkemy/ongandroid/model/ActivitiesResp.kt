package com.alkemy.ongandroid.model

data class ActivitiesResp(
    val id: Int = 0,
    val name:String = "",
    val slug: String = "",
    val description:String = "",
    val image: String = "",
    val user_id:Int = 0,
    val category_id:Int = 0,
    val created_at:String = "",
    val updated_at:String = "",
    val deleted_at:String? = null,
    val group_id:Int? = null
)
