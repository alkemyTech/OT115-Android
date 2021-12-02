package com.alkemy.ongandroid.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashVM by viewModels<SplashViewModel>()

    companion object {
        private const val DELAY_TIME = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTimer()

        val token = splashVM.getToken()

        if (token != ""){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }
    }

    private fun startTimer() = GlobalScope.launch {
        delay(DELAY_TIME)
        withContext(Dispatchers.Main) {
            Toast.makeText(
                applicationContext,
                getString(R.string.timer_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}