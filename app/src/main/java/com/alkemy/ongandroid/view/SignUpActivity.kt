package com.alkemy.ongandroid.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivitySignUpBinding
import com.alkemy.ongandroid.model.User
import com.alkemy.ongandroid.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeComponents()
        setUpObservers()
        onSaveUserBtnClick()
    }

    private fun initializeComponents() {
        disableSaveButton()
        binding.etUsername.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ -> captureAndSendFields() }
        binding.etEmail.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ -> captureAndSendFields() }
        binding.etPassword.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ -> captureAndSendFields() }
        binding.etConfirmPassword.doAfterTextChanged {
            captureAndSendFields()
            setPasswordErrorMessage()
        }
        attachLoadingProgressBar(binding.root)
    }

    private fun captureAndSendFields() {
        with(binding){
            tilConfirmPassword.isErrorEnabled = false

            val username: String = etUsername.text.toString()
            val email: String = etEmail.text.toString()
            val password: String = etPassword.text.toString()
            val confirmPassword: String = etConfirmPassword.text.toString()

            viewModel.validateFields(username, email, password, confirmPassword)
            viewModel.comparePasswords(password, confirmPassword)
        }
    }

    private fun disableSaveButton() {
        viewModel.isButtonSaveEnabled.observe(this) { buttonState ->
            if (buttonState) {
                enableSaveButton()
            } else {
                binding.btnSaveUser.isEnabled = false
                binding.btnSaveUser.setBackgroundColor(Color.LTGRAY)
            }
        }
    }

    private fun setPasswordErrorMessage() {
        viewModel.arePasswordsTheSame.observe(this) { passwordMatch ->
            if (!passwordMatch) {
                binding.tilConfirmPassword.error = getString(R.string.error_passwords_matches)
                binding.tilConfirmPassword.isErrorEnabled = true
            }
        }
    }

    private fun enableSaveButton() {
        binding.btnSaveUser.isEnabled = true
        binding.btnSaveUser.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.ong_color
            )
        )
    }

    private fun setUpObservers() {
        viewModel.state.observe(this, Observer {
            when (it) {
                is SignUpViewModel.State.Success -> handleSuccessState()
                //is SignUpViewModel.State.Failure -> //TODO
            }
        })

        viewModel.progressBarStatus.observe(this) {
            setCustomProgressBarVisibility(it)
        }
    }

    private fun onSaveUserBtnClick() {
        with(binding) {
            btnSaveUser.setOnClickListener {
                val name = etUsername.toString()
                val email = etEmail.toString()
                val pass = etPassword.toString()
                val user = User(name, email, pass)
                viewModel.addUserToRemoteDB(user)
            }
        }
    }

    private fun handleSuccessState() {
        showDialog()
        onBackPressed()
    }

    private fun showDialog() {
        val layout = binding.rootLayout
        val snackbar = Snackbar.make(
            layout,
            getString(R.string.user_success_sign_up),
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }
}