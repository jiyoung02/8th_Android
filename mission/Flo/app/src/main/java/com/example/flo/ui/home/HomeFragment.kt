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
import com.example.flo.DB.SongDatabase
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.data.Album
import com.example.flo.data.Song
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.ui.album.AlbumFragment
import com.example.flo.ui.home.HomeAlbumRVAdapter.clickListener
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private val TAG = javaClass.simpleName
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var songDB : SongDatabase
    private var albumData = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        songDB =  SongDatabase.getIntance(requireContext())!!
        initBanner()
        initPanel()

        // DB에서 앨범 데이터 읽어오기
        albumData.addAll(songDB.albumDao().getAlbums())

        val adapter = HomeAlbumRVAdapter(albumData, object : clickListener{
            override fun moveAlbumFragment(id: Int) {
                changeAlbumFragment(id)
            }
            override fun playAlbum(song: Song){
                (activity as MainActivity).setMiniPlayer(song)
            }

        })

        binding.rvTodayMusic.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTodayMusic.adapter = adapter
        return  binding.root
    }


    private fun changeAlbumFragment(id : Int) {
        (context as MainActivity).supportFragmentManager.beginTransaction().
        replace(R.id.nav_host_fragment_activity_main, AlbumFragment().apply{
            arguments = Bundle().apply {
                putInt("albumId", id)
            }
        }).commitAllowingStateLoss()
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


