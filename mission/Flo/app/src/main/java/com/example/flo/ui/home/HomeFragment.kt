package com.example.flo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.ui.album.AlbumFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val panelAdapter = HomePanelVPAdapter(this)
        panelAdapter.addItem(HomePanelFragment())
        panelAdapter.addItem(HomePanelFragment())
        binding.vpHomePanel.adapter = panelAdapter
        //뷰페이져가 좌우로 스크롤 될 수 있도록 지정
        binding.vpHomePanel.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        val bannerAdapter = HomePanelVPAdapter(this)
        bannerAdapter.addItem(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addItem(HomeBannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addItem(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addItem(HomeBannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.vpHomeBanner.adapter = bannerAdapter
        binding.vpHomeBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.ivAlbumImage1.setOnClickListener {
            // replace ( 바꿀 화면 id, 반영할 프래그먼트)
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_activity_main,
                AlbumFragment()
            ).commitAllowingStateLoss()
        }

        return  binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}