package com.example.flo.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment: Fragment() {
    private var _binding : FragmentAlbumBinding ? = null
    private val binding get() = _binding!!
    private val information  = arrayListOf("수록곡", "상세정보", "영상")
    private var title : String = ""
    private var singer : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumBinding.inflate(inflater,container,false)

        title = arguments?.getString("title")!!
        singer = arguments?.getString("singer")!!

        binding.tvAlbumTitle.text = title
        binding.tvAlbumSinger.text = singer

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}