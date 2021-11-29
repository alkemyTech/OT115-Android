package com.alkemy.ongandroid.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

        binding.btn_save_user.setOnClickListener {
            val name = binding.et_username.toString()
            val email = binding.et_email.toString()
            val pass = binding.et_password.toString()
            val user = User(name, email, pass)
            viewModel.addUserToRemoteDB(user)
            viewModel.state.observe(this, Observer {
                when (it) {
                    is SignUpViewModel.State.Success -> handleSuccessState()
                    //is SignUpViewModel.State.Failure -> //TODO
                }
            })
        }
    }

    private fun handleSuccessState(){
        showDialog()
        backToLogin()
    }

    private fun backToLogin() {
        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun showDialog() {
        var layout = binding.rootLayout
        val snackbar = Snackbar.make(
            layout,
            "User was successfully register",
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }
}
