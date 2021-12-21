package com.alkemy.ongandroid.model

data class ApiMembersResponse(
    val success:Boolean = false,
    val data: List<Member> = listOf(),
    val message: String = ""
)