package com.alkemy.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ItemUsBinding
import com.alkemy.ongandroid.model.RowMembers


class UsAdapter(val list : List<RowMembers> ):
    RecyclerView.Adapter<UsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(list[position]) {
                binding.ivUsPhotoOne.setImageResource(R.drawable.maria_garcia)
                binding.tvUsNameOne.text = this.first.name
                binding.tvUsPositionOne.text = this.first.jobposition

                binding.ivUsPhotoTwo.setImageResource(R.drawable.maria_garcia)
                binding.tvUsNameTwo.text = this.second.name
                binding.tvUsPositionTwo.text = this.second.jobposition

                binding.ivUsPhotoThree.setImageResource(R.drawable.maria_garcia)
                binding.tvUsNameThree.text = this.third.name
                binding.tvUsPositionThree.text = this.third.jobposition

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemUsBinding) :
        RecyclerView.ViewHolder(binding.root)

}