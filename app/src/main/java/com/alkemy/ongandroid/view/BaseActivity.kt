package com.alkemy.ongandroid.view

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    fun attachLoadingProgressBar(view: ViewGroup) {
        progressBar = ProgressBar(applicationContext)
        progressBar.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        progressBar.visibility = View.GONE

        view.addView(progressBar)
    }

    fun setCustomProgressBarVisibility(state: Boolean) {
        if (state) showLoadingSpinner()
        else hideLoadingSpinner()
    }

    private fun showLoadingSpinner() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingSpinner() {
        progressBar.visibility = View.GONE
    }

}