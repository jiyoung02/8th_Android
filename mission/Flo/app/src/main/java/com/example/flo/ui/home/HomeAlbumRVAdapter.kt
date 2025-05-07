package com.example.flo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.Album
import com.example.flo.databinding.ItemRvHomeAlbumBinding

class HomeAlbumRVAdapter (
    private val albumList : ArrayList<Album>
) : RecyclerView.Adapter<HomeAlbumRVAdapter.ViewHolder>() {

//    interface itemClick{
//        fun onItemClick
//    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRvHomeAlbumBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(albumList[position])
    }

    override fun getItemCount(): Int  = albumList.size


    inner class ViewHolder(val binding: ItemRvHomeAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.ivAlbumImage.setImageResource(album.coverImg!!)
            binding.tvAlbumTitle.text = album.title
            binding.tvAlbumSinger.text = album.singer
        }
    }
}