package com.alkemy.ongandroid.view

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.businesslogic.PASSWORD_REGEX
import com.alkemy.ongandroid.databinding.ActivitySignUpBinding
import java.util.regex.Pattern
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alkemy.ongandroid.model.User
import com.alkemy.ongandroid.viewmodel.SignUpViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    companion object{
        private const val SPECIAL_CHARACTERS_REGEX = "?=.*[\\u0020-\\u002F\\u003A-\\u0040\\u005B-\\u0060\\u007B-\\u007E]"
        private const val PASSWORD_REGEX = "^" +
                "(?=.*[0-9])" +                 //at least 1 digit
                "(?=.*[a-zA-Z])" +              //any letter
                "($SPECIAL_CHARACTERS_REGEX)" + //at least 1 special character
                ".{4,}\$"                       //at least 4 characters
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeComponents()

        setUpObservers()
        onSaveUserBtnClick()
    }

    private fun initializeComponents()
    {
        disableSaveButton()
        binding.etUsername.onFocusChangeListener = View.OnFocusChangeListener { _, _ -> validateFields() }
        binding.etEmail.onFocusChangeListener = View.OnFocusChangeListener { _, _ -> validateFields() }
        binding.etPassword.onFocusChangeListener = View.OnFocusChangeListener { _, _ -> validateFields() }
        binding.etConfirmPassword.onFocusChangeListener = View.OnFocusChangeListener { _, _ -> validateFields() }
        attachLoadingProgressBar(binding.root)
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
                binding.tilConfirmPassword.error = getString(R.string.error_passwords_matches)
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

    private fun isValidPasswordFormat(password: String): Boolean {
        return Pattern.matches(PASSWORD_REGEX, password)
    }

    private fun setUpObservers() {
        viewModel.state.observe(this, Observer {
            when (it) {
                is SignUpViewModel.State.Success -> handleSuccessState()
                is SignUpViewModel.State.Failure -> apiErrorView()
            }
        })

        viewModel.progressBarStatus.observe(this){
            setCustomProgressBarVisibility(it)
        }
    }

    private fun onSaveUserBtnClick() {
        with(binding) {
            this.btnSaveUser.setOnClickListener {
                val name = this.etUsername.toString()
                val email = this.etEmail.toString()
                val pass = this.etPassword.toString()
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

    private fun apiErrorView() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.error)
            .setMessage(R.string.sign_up_error)
            .setPositiveButton(R.string.ok) { dialog, which ->
                binding.tilPassword.isEndIconVisible = false
                binding.etEmail.error = getString(R.string.error)
                binding.etPassword.error = getString(R.string.error)
                binding.etUsername.error = getString(R.string.error)
                binding.etConfirmPassword.error = getString(R.string.error)
            }
            .show()

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