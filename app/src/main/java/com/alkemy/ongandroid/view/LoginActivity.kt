package com.alkemy.ongandroid.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginBinding
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginVM: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachLoadingProgressBar(binding.mainView)

        initializeComponents()

        setUpButtons()
        setUpObservers()
    }

    private fun setUpObservers() {
        loginVM.state.observe(this, Observer {
            when (it) {
                is LoginViewModel.State.Success -> navigateToMainScreen()
            }
        })
        loginVM.progressBarStatus.observe(this) {
            setCustomProgressBarVisibility(it)
        }
    }

    private fun setUpButtons() {
        binding.btnSignUp.setOnClickListener {
            navigateToSignUpScreen()
        }
        binding.btnLogin.setOnClickListener {
            loginVM.login(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
    }

    private fun initializeComponents() {
        disableLoginButton()
        binding.editTextEmail.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ -> changeStateLoginButton(binding.editTextEmail.text.toString(),  binding.editTextPassword.text.toString())
            }
        binding.editTextPassword.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ -> changeStateLoginButton(binding.editTextEmail.text.toString(),  binding.editTextPassword.text.toString()) }
    }

    private fun navigateToSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }
    private fun changeStateLoginButton(email : String, password : String){
        if(loginVM.validateFields(email, password)){
            enableLoginButton()
        } else {
            disableLoginButton()
        }
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

}
