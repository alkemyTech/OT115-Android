package com.alkemy.ongandroid.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
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
//                itemView.setOnLongClickListener {
//                    description.visibility= View.VISIBLE
//                    description.isVisible=true
//                    Toast.makeText(root.context, "funciona", Toast.LENGTH_LONG).show()
//                    true
//                }
                itemView.setOnTouchListener(object : View.OnTouchListener{
                    @SuppressLint("ClickableViewAccessibility")
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        when(event?.action){
                            MotionEvent.ACTION_DOWN -> {
                                description.visibility= View.VISIBLE
                            }
                            MotionEvent.ACTION_UP -> {
                                description.visibility= View.GONE
                            }
                        }
                        return true
                    }
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return actList.size
    }


    inner class ViewHolder(val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root)
}