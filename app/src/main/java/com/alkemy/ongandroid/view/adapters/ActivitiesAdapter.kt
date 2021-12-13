package com.alkemy.ongandroid.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ItemActivityBinding
import com.alkemy.ongandroid.model.ActivitiesResp
import com.bumptech.glide.Glide

class ActivitiesAdapter(private val actList: List<ActivitiesResp>) :
    RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemActivityBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(binding) {
                Glide.with(root.context).load(actList[position].image).into(actImg)
                actTxt.text = actList[position].name
                description.text = actList[position].description

                cardViewAct.animation = AnimationUtils.loadAnimation(
                    root.context,
                    R.anim.animation_rv_activities
                )

                loadOnPressAnimation(itemView, description, actTxt)
            }
        }
    }

    override fun getItemCount(): Int {
        return actList.size
    }

    inner class ViewHolder(val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("ClickableViewAccessibility")
    private fun loadOnPressAnimation(itemView: View, description: TextView, title: TextView ){
        itemView.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    itemView.setOnLongClickListener{
                        val fadeIn = AlphaAnimation(0.0f, 1.0f)
                        description.startAnimation(fadeIn)
                        fadeIn.duration = 750
                        description.visibility = View.VISIBLE
                        title.textSize = 20f
                        true
                    }
                }
                MotionEvent.ACTION_UP -> {
                    val fadeOut = AlphaAnimation(1.0f, 0.0f)
                    description.startAnimation(fadeOut)
                    fadeOut.duration = 500
                    fadeOut.setAnimationListener(object: Animation.AnimationListener{
                        override fun onAnimationStart(anim: Animation?) {}
                        override fun onAnimationRepeat(anim: Animation?) {}
                        override fun onAnimationEnd(anim: Animation?) {
                            description.visibility = View.GONE
                            title.textSize = 30f
                        }
                    })
                }
            }
            false
        }
    }

}