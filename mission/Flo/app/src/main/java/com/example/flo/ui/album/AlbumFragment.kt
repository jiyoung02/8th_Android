package com.example.flo.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private var title : String = ""
    private var singer : String = ""
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson,Album::class.java)
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