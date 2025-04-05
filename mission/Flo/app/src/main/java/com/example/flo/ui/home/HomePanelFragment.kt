package com.example.flo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.flo.databinding.ItemVpHomePanelBinding

class HomePanelFragment(private val imgRes : Int): Fragment() {
    lateinit var binding : ItemVpHomePanelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemVpHomePanelBinding.inflate(inflater,container,false)
        binding.root.background = ContextCompat.getDrawable(requireContext(),imgRes)
        return binding.root

    }
}