package com.example.flo.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.ActivitySongBinding

class SongActivity: AppCompatActivity() {
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer : Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        song = intent.getParcelableExtra<Song>("song")!!
        startTimer()
        setPlayer()
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(MainActivity.STRING_INTENT_KEY, "Return Song Title : $title")
            }
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        binding.ivControllerPlay.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.ivControllerPause.setOnClickListener {
            setPlayerStatus(false)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    @SuppressLint("DefaultLocale")
    private fun setPlayer(){
        binding.tvSongTitle.text = song.title
        binding.tvSongSinger.text = song.singer
        binding.tvStartTime.text = String.format("%02d:%02d",song.second/60 , song.second%60)
        binding.tvEndTime.text = String.format("%02d:%02d",song.playTime/60 , song.playTime%60)
        binding.sbSongProgress.progress =(song.second * 1000 / song.playTime)
        setPlayerStatus(song.isPlaying)

    }


    private fun setPlayerStatus(isPlaying: Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying
        if(isPlaying){
            binding.ivControllerPlay.visibility = View.GONE
            binding.ivControllerPause.visibility = View.VISIBLE
        }
        else{
            binding.ivControllerPlay.visibility = View.VISIBLE
            binding.ivControllerPause.visibility = View.GONE
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    // 코틀린에서 inner class 를 사용하지 않으면 정적인 변수 취급이라 내부 변수에 접근 불가능 -> binding 사용 불가!
    inner class Timer(private  val playTime: Int, var isPlaying: Boolean = true) : Thread(){
        private var second : Int = 0
        private var mills : Float = 0f

        @SuppressLint("DefaultLocale")
        override fun run() {
            super.run()
            try {
                while (true){
                    if(second >= playTime) break
                    if (isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.sbSongProgress.progress = ((mills / playTime)*10).toInt()
                        }

                        if(mills %1000 == 0f){
                            runOnUiThread {
                                binding.tvStartTime.text = String.format("%02d:%02d",second/60 ,second%60)
                            }
                            second++
                        }
                    }
                }
            }catch (e : InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다 ${e.message}")
            }

        }
    }


}