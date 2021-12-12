package com.alkemy.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ItemActivityBinding
import com.alkemy.ongandroid.model.ActivitiesResp
import com.bumptech.glide.Glide

class ActivitiesAdapter(val actList:List<ActivitiesResp>):
    RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemActivityBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.binding.root.context).load(actList[position].image).into(holder.binding.actImg)
        holder.binding.actTxt.text = actList[position].name
    }

    override fun getItemCount(): Int {
        return actList.size
    }


    inner class ViewHolder(val binding: ItemActivityBinding): RecyclerView.ViewHolder(binding.root)
}