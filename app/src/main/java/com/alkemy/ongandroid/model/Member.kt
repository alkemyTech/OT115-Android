package com.alkemy.ongandroid.model

data class Member(
    val image: String?,
    val name: String?,
    val jobposition: String?,
    val facebookUrl: String = "https://www.facebook.com/ONG-Educando-Somos-M%C3%A1s-110332257782506",
    val linkedinUrl: String = "https://www.linkedin.com/company/somos-m%C3%A1s-ong/"
)
