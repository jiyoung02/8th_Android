package com.example.flo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.MainActivity
import com.example.flo.R
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
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(MainActivity.STRING_INTENT_KEY, "Return Song Title : $title")
            }
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        binding.ivControllerPlay.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.ivControllerPause.setOnClickListener {
            setPlayerStatus(true)
        }

    }



    private fun setPlayerStatus(isPlaying: Boolean) {
        if(isPlaying){
            binding.ivControllerPlay.visibility = View.VISIBLE
            binding.ivControllerPause.visibility = View.GONE
        }
        else{
            binding.ivControllerPlay.visibility = View.GONE
            binding.ivControllerPause.visibility = View.VISIBLE
        }
    }


}