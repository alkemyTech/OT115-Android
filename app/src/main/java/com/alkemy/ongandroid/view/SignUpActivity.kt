package com.alkemy.ongandroid.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivitySignUpBinding
import com.alkemy.ongandroid.model.UserRequest
import com.alkemy.ongandroid.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var userRequest: UserRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setUpButtons()
    }

    private fun setupObservers() {
        viewModel.state.observe(this) {
            when (it) {
                is SignUpViewModel.State.Success -> handleSuccessState()
                //is SignUpViewModel.State.Failure -> //TODO
            }
        }
    }

    private fun setUpButtons() {
        with(binding) {
            btnSaveUser.setOnClickListener {
                val name = etUsername.toString()
                val email = etEmail.toString()
                val pass = etPassword.toString()
                userRequest = UserRequest(name, email, pass)
            }
        }
        onSaveUserBtnClick()
    }

    private fun onSaveUserBtnClick() {
        viewModel.addUserToRemoteDB(userRequest)
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