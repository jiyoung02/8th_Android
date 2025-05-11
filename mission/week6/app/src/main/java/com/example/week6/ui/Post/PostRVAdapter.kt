package com.example.week6.ui.Post

import android.os.Parcel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week6.data.PostData
import com.example.week6.databinding.RvItemBannerBinding
import com.example.week6.databinding.RvItemPostBinding

class PostRVAdapter (
    private val itemList : List<PostData>
) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PostViewHolder {
        val binding: RvItemPostBinding =  RvItemPostBinding .inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size +1

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
       holder.bind(itemList[position])
    }

}