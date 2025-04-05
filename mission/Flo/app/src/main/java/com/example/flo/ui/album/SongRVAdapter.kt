package com.example.flo.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.Song
import com.example.flo.databinding.ItemRvSongBinding

class SongRVAdapter(
    private val itemList : ArrayList<Song>
): RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRvSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (pos : Int)  {
            val song = itemList[pos]
            binding.tvIndex.text = (pos+1).toString()
            binding.tvSongTitle.text= song.title
            binding.tvSongSinger.text = song.singer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvSongBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}