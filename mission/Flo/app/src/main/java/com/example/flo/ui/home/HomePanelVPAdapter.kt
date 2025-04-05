package com.example.flo.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePanelVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment){
    private val fragmentList = ArrayList<Fragment>()
    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    fun addItem(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size-1)
    }

}