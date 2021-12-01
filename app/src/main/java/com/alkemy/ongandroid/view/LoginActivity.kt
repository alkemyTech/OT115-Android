package com.alkemy.ongandroid.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginBinding
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginVM: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val SPECIAL_CHARACTERS_REGEX =
            "?=.*[\\u0020-\\u002F\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E]"
        private const val PASSWORD_REGEX = "^" +
                "(?=.*[0-9])" +                 //at least 1 digit
                "(?=.*[a-zA-Z])" +              //any letter
             //   "($SPECIAL_CHARACTERS_REGEX)" + //at least 1 special character
                ".{4,}\$"                       //at least 4 characters
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpButtons()

        initializeComponents()

        setUpObservers()
    }

    private fun setUpObservers() {
        loginVM.loginfo.observe(this) {
            Toast.makeText(this, it[0].data.user.email, Toast.LENGTH_LONG).show()
        }
    }


    private fun setUpButtons() {
        binding.btnSignUp.setOnClickListener {
            navigateToSignUpScreen()
        }
        binding.btnLogin.setOnClickListener{
            loginVM.login("admin@admin", "admin")
        }
    }

    private fun initializeComponents() {
        disableLoginButton()
        binding.editTextEmail.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ -> validateFields() }
        binding.editTextPassword.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ -> validateFields() }
    }

    private fun validateFields() {

        val email: String = binding.editTextEmail.text.toString()
        val password: String = binding.editTextPassword.text.toString()

        val fieldsEmpty: Boolean =
            email.isEmpty() || password.isEmpty()
        val emailFormat: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordsFormat: Boolean =
            isValidPasswordFormat(password)

        if (!fieldsEmpty && emailFormat && passwordsFormat)
            enableLoginButton()
        else {
            disableLoginButton()
        }
    }

    private fun navigateToSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun isValidPasswordFormat(password: String): Boolean {
        return Pattern.matches(LoginActivity.PASSWORD_REGEX, password)
    }

    private fun disableLoginButton() {
        binding.btnLogin.isEnabled = false
        binding.btnLogin.setBackgroundColor(Color.LTGRAY)
    }

    private fun enableLoginButton() {
        binding.btnLogin.isEnabled = true
        binding.btnLogin.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.ong_color
            )
        )
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

}
