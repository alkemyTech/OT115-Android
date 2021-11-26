package com.alkemy.ongandroid.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alkemy.ongandroid.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val DELAY_TIME = 5000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTimer()
    }

    private fun startTimer() = GlobalScope.launch {
        delay(DELAY_TIME)
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                getString(R.string.timer_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}