package com.example.flo.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.DB.SongDatabase
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity: AppCompatActivity() {
    private val TAG = javaClass.simpleName
    lateinit var binding : ActivitySongBinding

    lateinit var timer : Timer
    lateinit var spf : SharedPreferences
    private var mediaPlayer : MediaPlayer? = null
    private var gson : Gson = Gson()
    private val songs = arrayListOf<Song>()
    lateinit var songDB : SongDatabase
    private var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        spf = getSharedPreferences("song", MODE_PRIVATE)

        initPlaylist()
        initSong()
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(MainActivity.STRING_INTENT_KEY, "Return Songitg Title : $title")
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
    private fun initPlaylist(){
        songDB  =SongDatabase.getIntance(this)!!
        songs.addAll(songDB.songDao().getSongs())

    }

    private fun initSong(){
        val songId = spf.getInt("songId",0)
        nowPos = getPlayingSongPosition(songId)
        startTimer()
        setPlayer()
    }
    private fun getPlayingSongPosition(songId: Int): Int {
        for(i in 0 until songs.size){
            if(songs[i].id == songId)
                return i
        }
        return 0
    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second = ((binding.sbSongProgress.progress * songs[nowPos].playTime)/100)/100
        Log.d(TAG, "Pause : ${songs[nowPos]}")
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val editor = spf.edit()
        val songJson = gson.toJson(songs[nowPos])
        editor.putString("songData",songJson)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()  // 리소스 해제
        mediaPlayer = null // 플레이어 해제

    }

    @SuppressLint("DefaultLocale")
    private fun setPlayer(){
        binding.tvSongTitle.text = songs[nowPos].title
        binding.tvSongSinger.text = songs[nowPos].singer
        binding.tvStartTime.text = String.format("%02d:%02d",songs[nowPos].second/60 , songs[nowPos].second%60)
        binding.tvEndTime.text = String.format("%02d:%02d",songs[nowPos].playTime/60 , songs[nowPos].playTime%60)
        binding.sbSongProgress.progress =(songs[nowPos].second * 1000 / songs[nowPos].playTime)
        val music = resources.getIdentifier(songs[nowPos].music,"raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayerStatus(songs[nowPos].isPlaying)

    }


    private fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying
        if(isPlaying){
            binding.ivControllerPlay.visibility = View.GONE
            binding.ivControllerPause.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.ivControllerPlay.visibility = View.VISIBLE
            binding.ivControllerPause.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
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