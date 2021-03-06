package com.alkemy.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongandroid.databinding.WelcomeViewPagerItemBinding
import com.alkemy.ongandroid.model.Slide
import com.bumptech.glide.Glide

class WelcomeViewPagerAdapter(private val slideList: List<Slide>) : RecyclerView.Adapter<WelcomeViewPagerAdapter.ViewHolder>() {

    private lateinit var binding: WelcomeViewPagerItemBinding

    class ViewHolder(binding: WelcomeViewPagerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = WelcomeViewPagerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (slideList.isNotEmpty())
        {
            with(slideList.get(position % slideList.size)){
                binding.txtName.text = name
                binding.txtDescription.text = description
                Glide.with(holder.itemView.context)
                        .load(imageUrl)
                        .into(binding.imgPhoto)
            }
        }
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}