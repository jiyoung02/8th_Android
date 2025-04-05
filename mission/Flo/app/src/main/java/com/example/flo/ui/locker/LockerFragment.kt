package com.example.flo.ui.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.ui.album.AlbumVPAdapter
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment(){
    private var _binding : FragmentLockerBinding? = null
    private val binding get() = _binding!!
    private val information  = arrayListOf("저장한 곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLockerBinding.inflate(inflater,container,false)

        val adapter = LockerVPAdapter(this)
        binding.vpAlbumContents.adapter = adapter
        TabLayoutMediator(binding.tbLocker, binding.vpAlbumContents){
                tab, position->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}