package com.example.flo.ui.locker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.DB.SongDatabase
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.FragmentDownloadBinding
import com.example.flo.ui.locker.DownloadSongRVAdapter.ClickListener

class DownLoadFragment: Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var binding : FragmentDownloadBinding
    private var songList = ArrayList<Song>()
    lateinit var songDB : SongDatabase
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadBinding.inflate(inflater,container,false)
        songDB =  SongDatabase.getIntance(requireContext())!!
        songList.addAll(songDB.songDao().getLikedSongs(true))
        val adapter = DownloadSongRVAdapter(songList, object : ClickListener{
            override fun clickPlay(song: Song) {
               return
            }
            override fun clickMore(song: Song) {
                Log.d(TAG,"before ${songDB.songDao().getSong(song.id)}")
                songDB.songDao().updateIsLikeById(false,song.id)
                Log.d(TAG,"update ${songDB.songDao().getSong(song.id)}")
                return
            }
        })

        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        binding.tvEmpty.visibility = if (songList.isEmpty()) View.VISIBLE else View.GONE

        return binding.root
    }

}