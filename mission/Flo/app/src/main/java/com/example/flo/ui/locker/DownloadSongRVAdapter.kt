package com.example.flo.ui.locker

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.ItemRvDownloadSongBinding
import com.example.flo.ui.locker.DownloadSongRVAdapter.ViewHolder

class DownloadSongRVAdapter (
    private val itemList : ArrayList<Song>,
    private val clickListener : ClickListener
): RecyclerView.Adapter<ViewHolder>() {
    private var background : Int? = null

    inner class ViewHolder(private val binding: ItemRvDownloadSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (pos : Int)  {
            val song = itemList[pos]
            if (background!=null) binding.root.setBackgroundColor(background!!)
            binding.tvSongTitle.text= song.title
            binding.tvSongSinger.text = song.singer
            binding.ivSongImg.setImageResource(song.coverImg!!)

            binding.ivPlay.setOnClickListener { clickListener.clickPlay(song) }
            binding.ivMore.setOnClickListener {
                clickListener.clickMore(song)
                notifyItemRemoved(pos)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvDownloadSongBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun changeColor(color : Int){
        background = color
        notifyDataSetChanged()
    }
    interface ClickListener {
        fun clickPlay(song: Song)
        fun clickMore(song: Song)
    }
}