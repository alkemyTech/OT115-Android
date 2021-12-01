package com.alkemy.ongandroid.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

        setUpButtons()
        setUpObservers()
    }

    private fun setUpObservers() {
        loginVM.state.observe(this, Observer {
            when (it) {
                is LoginViewModel.State.Success -> navigateToMainScreen()
            }
        })
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

}