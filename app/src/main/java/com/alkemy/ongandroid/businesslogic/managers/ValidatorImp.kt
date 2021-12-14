package com.alkemy.ongandroid.businesslogic.managers

import android.util.Patterns
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import java.util.regex.Pattern

class ValidatorImp : Validator {

    override fun validateEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()


    override fun validatePassword(password: String) =
        Pattern.matches(LoginViewModel.PASSWORD_REGEX_WO_EC, password)
}