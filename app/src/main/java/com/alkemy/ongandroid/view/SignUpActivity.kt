package com.alkemy.ongandroid.view

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginBinding
import com.alkemy.ongandroid.databinding.ActivityMainBinding
import com.alkemy.ongandroid.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeComponents()
    }

    private fun initializeComponents()
    {
        disableSaveButton()
        binding.etUsername.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                validateFields()
            }
        })
        binding.etEmail.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                validateFields()
            }
        })
        binding.etPassword.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                validateFields()
            }
        })
        binding.etConfirmPassword.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                validateFields()
            }
        })
    }

    private fun validateFields()
    {
        binding.tilConfirmPassword.isErrorEnabled = false

        val username: String = binding.etUsername.text.toString()
        val email: String = binding.etEmail.text.toString()
        val password: String = binding.etPassword.text.toString()
        val confirmPassword: String = binding.etConfirmPassword.text.toString()

        val fieldsEmpty: Boolean = username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
        val emailFormat: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordsFormat: Boolean = isValidPasswordFormat(password) && isValidPasswordFormat(confirmPassword)
        val passwordsEquals: Boolean = (password == confirmPassword)

        if (!fieldsEmpty && emailFormat && passwordsFormat && passwordsEquals)
            enableSaveButton()
        else
        {
            disableSaveButton()
            if (!passwordsEquals)
            {
                binding.tilConfirmPassword.error = "Passwords are not the same"
                binding.tilConfirmPassword.isErrorEnabled = true
            }
        }
    }

    private fun disableSaveButton()
    {
        binding.btnSaveUser.isEnabled = false
        binding.btnSaveUser.setBackgroundColor(Color.LTGRAY)
    }

    private fun enableSaveButton()
    {
        binding.btnSaveUser.isEnabled = true
        binding.btnSaveUser.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.ong_color))
    }

    fun isValidPasswordFormat(password: String): Boolean {
        val SPECIAL_CHARACTERS_REGEX = "?=.*[\\u0020-\\u002F\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E]"
        val PASSWORD_REGEX = "^" +
                "(?=.*[0-9])" +                 //at least 1 digit
                "(?=.*[a-zA-Z])" +              //any letter
                "($SPECIAL_CHARACTERS_REGEX)" + //at least 1 special character
                ".{4,}\$"                       //at least 4 characters
        return Pattern.matches(PASSWORD_REGEX, password)
    }
}