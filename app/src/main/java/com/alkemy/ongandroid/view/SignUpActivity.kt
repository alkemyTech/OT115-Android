package com.alkemy.ongandroid.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ActivitySignUpBinding
import com.alkemy.ongandroid.model.User
import com.alkemy.ongandroid.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this, Observer {
            when (it) {
                is SignUpViewModel.State.Success -> handleSuccessState()
                //is SignUpViewModel.State.Failure -> //TODO
            }
        })
        onSaveUserBtnClick()
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
}
