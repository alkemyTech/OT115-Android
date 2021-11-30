package com.alkemy.ongandroid.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        loginVM.login("admin@admin", "admin")
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
    }

    private fun navigateToSignUpScreen() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }


    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

}
