package com.example.flo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity: AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    private var title = ""
    private var singer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        title = intent.getStringExtra("title").toString()
        singer = intent.getStringExtra("singer").toString()
        setContentView(binding.root)

        binding.tvSongTitle.text = title
        binding.tvSongSinger.text = singer
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

}