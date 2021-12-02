package com.alkemy.ongandroid.view.fragments.testimonials

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongandroid.databinding.TestimonialsItemBinding
import com.alkemy.ongandroid.model.Testimonial
import com.bumptech.glide.Glide


class TestimonialsAdapter(val testimonialList: List<Testimonial>) :
    RecyclerView.Adapter<TestimonialsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TestimonialsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(testimonialList[position]) {
                Glide.with(binding.root.context).load(image).into(binding.image)
                binding.name.text = name
                binding.description.text = description
            }
        }
    }

    override fun getItemCount(): Int {
        return testimonialList.size
    }

    inner class ViewHolder(val binding: TestimonialsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}