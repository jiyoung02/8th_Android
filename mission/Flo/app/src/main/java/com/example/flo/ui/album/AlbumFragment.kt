package com.example.flo.ui.album

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.DB.SongDatabase
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.data.Album
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment: Fragment() {
    private var _binding : FragmentAlbumBinding ? = null
    private val binding get() = _binding!!
    private val information  = arrayListOf("수록곡", "상세정보", "영상")
    lateinit var songDB : SongDatabase
    lateinit var spf : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumBinding.inflate(inflater,container,false)
        spf = requireContext().getSharedPreferences("app_data", MODE_PRIVATE)
        songDB = SongDatabase.getIntance(requireContext())!!
        val albumId = spf.getInt("albumId",0)
        val album = songDB.albumDao().getAlbum(albumId)
        initData(album)

        val albumAdapter = AlbumVPAdapter(this)
        binding.vpAlbumContents.adapter = albumAdapter
        TabLayoutMediator(binding.tbAlbumContents, binding.vpAlbumContents){
            tab, position->
            tab.text = information[position]
        }.attach()


        binding.ivBack.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_activity_main,
                HomeFragment()
            ).commitAllowingStateLoss()
        }
        return binding.root
    }

    private fun initData(album: Album) {
        binding.tvAlbumTitle.text = album.title
        binding.tvAlbumSinger.text = album.singer
        binding.ivAlbumImage.setImageResource(album.coverImg!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}