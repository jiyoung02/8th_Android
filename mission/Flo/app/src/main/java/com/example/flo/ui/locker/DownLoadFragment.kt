package com.example.flo.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.FragmentDownloadBinding
import com.example.flo.ui.locker.DownloadSongRVAdapter.ClickListener

class DownLoadFragment: Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var binding : FragmentDownloadBinding
    private var songList = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadBinding.inflate(inflater,container,false)
        initDummy()
        val adapter = DownloadSongRVAdapter(songList, object : ClickListener{
            override fun clickPlay(song: Song) {
               return
            }
            override fun clickMore(pos: Int) {
                songList.removeAt(pos)
                return
            }
        })

        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        binding.tvEmpty.visibility = if (songList.isEmpty()) View.VISIBLE else View.GONE

        return binding.root
    }

    private fun initDummy() {
        val imgList = listOf<Int>(R.drawable.img_album1,R.drawable.img_album2,R.drawable.img_album_exp,R.drawable.img_album_exp2)
        songList.apply {
            for (i in 1..10)
                add(Song(title = "Song${i}", singer = "Singer${i}", coverImg = imgList[i%4]))
        }
    }
}