package com.alkemy.ongandroid.businesslogic.managers

interface Validator {

    fun validateEmail(email: String): Boolean

    fun validatePassword(password: String): Boolean
}