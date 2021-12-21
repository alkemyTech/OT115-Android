package com.alkemy.ongandroid.view.activities

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivitySignUpBinding
import com.alkemy.ongandroid.viewmodel.SignUpViewModel
import com.alkemy.ongandroid.model.UserRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var userRequest: UserRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeComponents()

        errorViewsListeners()
        setUpObservers()
        onSaveUserBtnClick()
    }

    private fun initializeComponents() {
        with(binding) {

            etUsername.doAfterTextChanged { captureAndSendFields() }
            etEmail.doAfterTextChanged { captureAndSendFields() }
            etPassword.doAfterTextChanged { captureAndSendFields() }
            etConfirmPassword.doAfterTextChanged { captureAndSendFields() }

            attachLoadingProgressBar(root)
        }
    }

    private fun captureAndSendFields() {
        with(binding) {
            tilConfirmPassword.isErrorEnabled = false

            val username: String = etUsername.text.toString()
            val email: String = etEmail.text.toString()
            val password: String = etPassword.text.toString()
            val confirmPassword: String = etConfirmPassword.text.toString()

            viewModel.validateFields(username, email, password, confirmPassword)
            viewModel.comparePasswords(password, confirmPassword)
        }
    }

    private fun disableSaveButton(buttonState: Boolean) {

        if (buttonState) {
            enableSaveButton()
        } else {
            binding.btnSaveUser.isEnabled = false
            binding.btnSaveUser.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun setPasswordErrorMessage(passwordMatch: Boolean) {
        if (!passwordMatch) {
            binding.tilConfirmPassword.error = getString(R.string.error_passwords_matches)
            binding.tilConfirmPassword.isErrorEnabled = true
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
        viewModel.state.observe(this, {
            when (it) {
                is SignUpViewModel.State.Success -> {
                    handleSuccessState()
                    viewModel.registerSignUpSuccess()
                }
                is SignUpViewModel.State.Failure -> {
                    apiErrorView()
                    viewModel.registerSignUpFailure()
                }
            }
        })

        viewModel.progressBarStatus.observe(this) {
            setCustomProgressBarVisibility(it)
        }

        viewModel.isButtonSaveEnabled.observe(this) {
            disableSaveButton(it)
        }
        viewModel.arePasswordsTheSame.observe(this) {
            setPasswordErrorMessage(it)
        }
    }

    private fun onSaveUserBtnClick() {
        with(binding) {
            btnSaveUser.setOnClickListener {
                val name = etUsername.toString()
                val email = etEmail.toString()
                val pass = etPassword.toString()
                viewModel.registerSignUpPressedEvent()
                userRequest = UserRequest(name, email, pass)
                viewModel.addUserToRemoteDB(userRequest)
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

    private fun apiErrorView() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.error)
            .setMessage(R.string.sign_up_error)
            .setPositiveButton(R.string.ok) { _, _ ->
                with(binding) {
                    tilPassword.isEndIconVisible = false
                    etEmail.error = getString(R.string.error)
                    etPassword.error = getString(R.string.error)
                    etUsername.error = getString(R.string.error)
                    etConfirmPassword.error = getString(R.string.error)
                }
            }
            .show()
    }

    private fun errorViewsListeners() {
        binding.etConfirmPassword.addTextChangedListener {
            cancelErrorViews()
        }
        binding.etPassword.addTextChangedListener {
            cancelErrorViews()
        }
        binding.etEmail.addTextChangedListener {
            cancelErrorViews()
        }
        binding.etUsername.addTextChangedListener {
            cancelErrorViews()
        }
    }

    private fun cancelErrorViews() {
        binding.etUsername.error = null
        binding.etEmail.error = null
        binding.etPassword.error = null
        binding.etConfirmPassword.error = null
        binding.tilPassword.isEndIconVisible = true
        binding.tilConfirmPassword.isEndIconVisible = true

    }
}