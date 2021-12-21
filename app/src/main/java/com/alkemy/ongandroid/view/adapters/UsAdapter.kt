package com.alkemy.ongandroid.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkemy.ongandroid.R
import com.alkemy.ongandroid.databinding.ItemUsBinding
import com.alkemy.ongandroid.model.Member
import com.alkemy.ongandroid.view.activities.MemberDetailActivity
import com.bumptech.glide.Glide


class UsAdapter(val list : List<Member> ):
    RecyclerView.Adapter<UsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val member = list[position]
        with(holder.binding){
            with(member) {
                Glide.with(root.context).load(this.image).placeholder(R.drawable.foto4).into(ivUsPhoto)
                tvUsName.text = this.name  ?: root.context.getString(R.string.without_info)
                tvUsPosition.text = this.description  ?: root.context.getString(R.string.without_info)
            }
        }
        holder.binding.root.setOnClickListener {
            launchDetailActivity(it.context, member)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun launchDetailActivity(activity: Context, member: Member) {
        val intent = MemberDetailActivity.createIntent(
            activity = activity,
            image = member.image.toString(),
            name = member.name.toString(),
            position = member.jobposition.toString(),
            facebookURL = member.facebookUrl,
            linkedinURL = member.linkedinUrl
        )
        activity.startActivity(intent)
    }

    inner class ViewHolder(val binding: ItemUsBinding) :
        RecyclerView.ViewHolder(binding.root)

}