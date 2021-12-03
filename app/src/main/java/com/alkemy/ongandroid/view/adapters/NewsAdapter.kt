package com.alkemy.ongandroid.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ItemNewBinding
import com.alkemy.ongandroid.model.ApiNews
import com.alkemy.ongandroid.view.utils.DiffUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var context: Context
    private var initialList = mutableListOf<ApiNews>()


    fun setData(newList: MutableList<ApiNews>){
        val diffUtils = DiffUtils(initialList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        this.initialList = newList
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return NewsViewHolder(layoutInflater.inflate(R.layout.item_new, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = initialList[position]
        with(holder.binding){
            Glide.with(context).load(item.image).transform(RoundedCorners(16)).into(imgNew)
            tvNewTitle.text = item.name
            tvNewDescription.text = item.content
        }
    }

    override fun getItemCount(): Int = initialList.size


    inner class NewsViewHolder(view:View): RecyclerView.ViewHolder(view){
        val binding = ItemNewBinding.bind(view)
    }
}