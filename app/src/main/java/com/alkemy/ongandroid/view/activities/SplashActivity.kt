package com.alkemy.ongandroid.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashVM by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpObserver()
        splashVM.getToken()
    }

    private fun setUpObserver() {
        splashVM.existToken.observe(this, {
            when (it) {
                 false -> startActivity(Intent(this, LoginActivity::class.java))
                 true -> startActivity(Intent(this, LoginSuccessActivity::class.java))
            }
        })
    }
}