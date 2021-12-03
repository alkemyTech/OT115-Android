package com.alkemy.ongandroid.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivityLoginBinding
import com.alkemy.ongandroid.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val loginVM: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachLoadingProgressBar(binding.mainView)
        setUpObservers()
        initializeComponents()

        setUpButtons()

    }

    private fun setUpObservers() {
        loginVM.state.observe(this, {
            when (it) {
                is LoginViewModel.State.Success -> navigateToMainScreen()
            }
        })

        loginVM.progressBarStatus.observe(this) {
            setCustomProgressBarVisibility(it)
        }

        loginVM.viewState.observe(this, {
            when (it) {
                true -> enableLoginButton()
                false -> disableLoginButton()
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

    private fun initializeComponents() {
        disableLoginButton()
        binding.editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginVM.validateFields(binding.editTextEmail.text.toString(),  binding.editTextPassword.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginVM.validateFields(binding.editTextEmail.text.toString(),  binding.editTextPassword.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
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
