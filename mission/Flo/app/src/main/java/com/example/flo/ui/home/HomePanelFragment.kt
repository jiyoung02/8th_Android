package com.example.flo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.ItemVpHomePanelBinding

class HomePanelFragment: Fragment() {
    lateinit var binding : ItemVpHomePanelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemVpHomePanelBinding.inflate(inflater,container,false)
        return binding.root

    }
}