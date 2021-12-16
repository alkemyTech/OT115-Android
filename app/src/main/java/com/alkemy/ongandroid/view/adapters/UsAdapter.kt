package com.alkemy.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ItemUsBinding
import com.alkemy.ongandroid.model.Member
import com.bumptech.glide.Glide


class UsAdapter(val list : List<Member> ):
    RecyclerView.Adapter<UsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            with(list[position]) {
                Glide.with(root.context).load(this.image).placeholder(R.drawable.foto4).into(ivUsPhoto)
                tvUsName.text = this.name  ?: root.context.getString(R.string.without_info)
                tvUsPosition.text = this.description  ?: root.context.getString(R.string.without_info)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemUsBinding) :
        RecyclerView.ViewHolder(binding.root)

}