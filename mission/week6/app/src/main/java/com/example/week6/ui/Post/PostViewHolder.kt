package com.example.week6.ui.Post

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.week6.data.PostData
import com.example.week6.databinding.RvItemPostBinding

class PostViewHolder(val binding : RvItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : PostData){
        binding.tvTitle.text = item.title
        binding.tvContents.text = item.content
        binding.tvDate.text = item.date
        binding.tvName.text = item.name
        binding.tvBar.visibility = View.GONE
    }
}