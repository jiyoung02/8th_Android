package com.example.flo.ui.locker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.DB.SongDatabase
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.FragmentDownloadBinding
import com.example.flo.ui.locker.DownloadSongRVAdapter.ClickListener

class DownLoadFragment: Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var binding : FragmentDownloadBinding
    private var songList = ArrayList<Song>()
    lateinit var songDB : SongDatabase
    private var isSelectAll = false
    lateinit var adapter : DownloadSongRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadBinding.inflate(inflater,container,false)
        songDB =  SongDatabase.getIntance(requireContext())!!

        songList.addAll(songDB.songDao().getLikedSongs(true))
        adapter = DownloadSongRVAdapter(songList, object : ClickListener{
            override fun clickPlay(song: Song) {
                return
            }
            override fun clickMore(song: Song) {
                songList.remove(song)
                songDB.songDao().updateIsLikeById(false,song.id)
                Log.d(TAG,"update ${songDB.songDao().getSong(song.id)}")
                return
            }
        })

        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.tvEmpty.visibility = if (songList.isEmpty()) View.VISIBLE else View.GONE

        binding.layoutSelectAll.setOnClickListener {
            isSelectAll = !isSelectAll
            val bottomSheet = customBottomSheet()
            changeColors()
            bottomSheet.listener = object : customBottomSheet.BottomSheetListener{
                    override fun deleteItem() {
                        for(song : Song in songList) songDB.songDao().updateIsLikeById(false,song.id)
                        songList.clear()
                        Log.d("test","clickdelete")
                    }

                    override fun dismissSheet() {
                        isSelectAll = false
                        changeColors()
                    }
                }
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }
    private fun changeColors(){
        if (isSelectAll){
            val color = ContextCompat.getColor(requireContext(),R.color.select_color)
            binding.tvSelectAll.text = "선택해제"
            binding.tvSelectAll.setTextColor(color)
            binding.ivSelectAll.setColorFilter(color)
            adapter.changeColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        }
        else{
            val color = ContextCompat.getColor(requireContext(),R.color.gray_color)
            binding.tvSelectAll.text = "전체선택"
            binding.tvSelectAll.setTextColor(color)
            binding.ivSelectAll.setColorFilter(color)
            adapter.changeColor(ContextCompat.getColor(requireContext(), R.color.white))
        }

    }


}