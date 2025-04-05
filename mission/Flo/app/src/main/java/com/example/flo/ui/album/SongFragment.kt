package com.example.flo.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.FragmentSongBinding

class SongFragment: Fragment() {
    lateinit var binding : FragmentSongBinding
    private val songList = arrayListOf<Song>()
    private var mixFlag : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)
        initDummy()
        val adapter = SongRVAdapter(songList)
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        binding.layoutMix.setOnClickListener {
            val imgRes = if (mixFlag) R.drawable.btn_toggle_off else R.drawable.btn_toggle_on
            binding.btnMixToggle.setImageResource(imgRes)
            mixFlag = !mixFlag
        }

        return binding.root
    }

    private fun initDummy(){
        for( i in  1..7)
            songList.add(Song("title${i}","singer${i}"))

    }
}