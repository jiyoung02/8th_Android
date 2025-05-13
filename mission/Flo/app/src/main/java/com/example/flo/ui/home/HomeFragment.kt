package com.example.flo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.data.Album
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.ui.album.AlbumFragment
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private val TAG = javaClass.simpleName
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var albumData = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initBanner()
        initPanel()
        initDummy()

        val adapter = HomeAlbumRVAdapter(albumData) {album ->
            Log.d(TAG,"click : ${album}")
            changeAlbumFragment(album)
        }

        binding.rvTodayMusic.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTodayMusic.adapter = adapter
        return  binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction().
        replace(R.id.nav_host_fragment_activity_main, AlbumFragment().apply{
            arguments = Bundle().apply {
                val gson = Gson()
                val albumJson = gson.toJson(album)
                putString("album",albumJson)
            }
        }).commitAllowingStateLoss()
    }

    private fun initDummy(){
        albumData.apply {
            add( Album("Lilac","아이유",R.drawable.img_album_exp2))
            add( Album("IVE EMPATHY","아이브", R.drawable.img_album1))
            add( Album("Whiplash","에스파",R.drawable.img_album2))
            add(Album("Butter","방탄소년단",R.drawable.img_album_exp))
            add( Album("Lilac","아이유",R.drawable.img_album_exp2))
            add( Album("IVE EMPATHY","아이브", R.drawable.img_album1))
        }

    }

    private fun initPanel() {
        val panelAdapter = HomePanelVPAdapter(this)
        val bundle = Bundle()
        panelAdapter.addItem(HomePanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addItem(HomePanelFragment(R.drawable.ic_launcher_background))
        panelAdapter.addItem(HomePanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addItem(HomePanelFragment(R.drawable.ic_launcher_background))
        binding.vpHomePanel.adapter = panelAdapter
        //뷰페이져가 좌우로 스크롤 될 수 있도록 지정
        binding.vpHomePanel.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // indicator 설정
        binding.panelIndicator.setViewPager(binding.vpHomePanel)

    }

    private fun initBanner() {
        val bannerAdapter = HomePanelVPAdapter(this)
        bannerAdapter.addItem(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addItem(HomeBannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addItem(HomeBannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addItem(HomeBannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.vpHomeBanner.adapter = bannerAdapter
        binding.vpHomeBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


