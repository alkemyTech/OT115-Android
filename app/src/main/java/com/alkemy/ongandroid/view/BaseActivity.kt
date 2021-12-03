package com.alkemy.ongandroid.view

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.alkemy.ongandroid.R

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    fun attachLoadingProgressBar(view: ConstraintLayout) {
        progressBar = layoutInflater.inflate(R.layout.custom_progress_bar, null) as ProgressBar

        progressBar.apply {
            val constraintLayoutParams: ConstraintLayout.LayoutParams =
                ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
            constraintLayoutParams.apply {
                bottomToBottom = ConstraintSet.PARENT_ID
                endToEnd = ConstraintSet.PARENT_ID
                startToStart = ConstraintSet.PARENT_ID
                topToTop = ConstraintSet.PARENT_ID
            }

            layoutParams = constraintLayoutParams
        }


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