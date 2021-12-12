package com.alkemy.ongandroid.model

data class ApiTestimonialsResponse (
    val success:Boolean = false,
    val data: List<Testimonial> = listOf(),
    val message: String = ""
)

