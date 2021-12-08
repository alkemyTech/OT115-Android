package com.alkemy.ongandroid.view.utils

import androidx.recyclerview.widget.DiffUtil
import com.alkemy.ongandroid.model.ApiNews

class DiffUtils(
    private val oldList: MutableList<ApiNews>,
    private val newList: MutableList<ApiNews>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].image == newList[newItemPosition].image
    }
}