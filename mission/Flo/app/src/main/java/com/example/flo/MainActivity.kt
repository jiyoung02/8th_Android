package com.example.flo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.flo.DB.SongDatabase
import com.example.flo.data.Album
import com.example.flo.data.Song
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.ui.SongActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer : MediaPlayer? = null
    lateinit var spf : SharedPreferences
    private val songs = arrayListOf<Song>()
    private var nowPos = 0
    lateinit var songDB : SongDatabase
    private var timer : Timer? = null
    companion object{
        const val STRING_INTENT_KEY = "my_string_key"
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result->
            if(result.resultCode == Activity.RESULT_OK){
                val returnString = result.data?.getStringExtra(STRING_INTENT_KEY)
                Toast.makeText(this,returnString,Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spf = getSharedPreferences("songId", MODE_PRIVATE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        songDB = SongDatabase.getIntance(this)!!
        setContentView(binding.root)
        initDummyAlbum()
        inputDummySongs()
        initClickListner()
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_look,R.id.navigation_locker
            )
        )


        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        val songId = spf.getInt("songId",0)
        if(songId == 0) binding.tvSongTitle.text = "재생중인 노래가 없습니다"
        else {
            val albumId = songDB.songDao().getAlbumId(songId)
            initSong(albumId, songId)
        }

    }

    fun initSong(albumId : Int, songId : Int){
        songs.addAll(songDB.albumDao().getSongsByAlbumId(albumId))
        nowPos = songId
        for (i in 0.. songs.size-1){
            if (songs[i].id == songId){
                nowPos = i
                break
            }
        }
        setMiniPlayer(songs[nowPos])
    }

    private fun setMiniPlayer(song : Song){
        binding.layoutMiniplayer.setOnClickListener{
            spf.edit().putInt("songId",song.id).apply()
            val intent = Intent(this,SongActivity::class.java)
            getResult.launch(intent)
        }
        binding.tvSongTitle.text = song.title
        binding.tvSongSinger.text = song.singer
        mediaPlayer?.release()
        val music = resources.getIdentifier(song.music,"raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        if (timer!= null) timer!!.interrupt()
        startTimer()
        setPlayerStatus(true)

    }

    private fun initClickListner(){
        binding.ivMinipalyerPlay.setOnClickListener {
            if (songs[nowPos].isPlaying) setPlayerStatus(false)
            else setPlayerStatus(true)
        }
        binding.ivMinipalyerPrev.setOnClickListener {
            moveSong(-1)
        }
        binding.ivMinipalyerNext.setOnClickListener {
            moveSong(1)
        }
    }
    private fun moveSong(direct : Int){
        if(nowPos+direct<0){
            Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos+direct >= songs.size){
            Toast.makeText(this,"last song", Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct
        timer!!.interrupt()
        startTimer()
        mediaPlayer?.release()
        mediaPlayer = null

        setMiniPlayer(songs[nowPos])
        setPlayerStatus(true)
    }
    private fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer!!.isPlaying = isPlaying
        if (isPlaying){
            binding.ivMinipalyerPlay.setImageResource(R.drawable.btn_miniplay_pause)
            mediaPlayer?.start()
        }else{
            binding.ivMinipalyerPlay.setImageResource(R.drawable.btn_miniplayer_play)
            if(mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
        }

    }

    private fun initDummyAlbum(){
        val albums = songDB.albumDao().getAlbums()
        if(albums.isNotEmpty()) return
        songDB.albumDao().insert(Album("Lilac","아이유",R.drawable.img_album_exp2))
        songDB.albumDao().insert( Album("IVE EMPATHY","아이브", R.drawable.img_album1))
        songDB.albumDao().insert( Album("Whiplash","에스파",R.drawable.img_album2))
        songDB.albumDao().insert(Album("Butter","방탄소년단",R.drawable.img_album_exp))
        songDB.albumDao().insert( Album("Lilac2","아이유",R.drawable.img_album_exp2))
        songDB.albumDao().insert( Album("IVE EMPATHY2","아이브", R.drawable.img_album1))

    }

    private fun inputDummySongs(){
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return
        songDB.songDao().insert(Song("라일락", "아이유", 0, 60, false, "iu_lilac", R.drawable.img_album_exp2, false, 1))
        songDB.songDao().insert(Song("attitude", "아이브", 0, 60, false, "ive_attitude", R.drawable.img_album1, false,2))
        songDB.songDao().insert(Song("라일락2", "아이유", 0, 60, false, "iu_lilac", R.drawable.img_album_exp2, false,1))
        songDB.songDao().insert(Song("attitude2", "아이브", 0, 60, false, "ive_attitude", R.drawable.img_album1, false,2))
        val _songs = songDB.songDao().getSongs()
        Log.d(TAG, "DB data: ${_songs}")
    }
    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer!!.start()
    }

    inner class Timer(private  val playTime: Int = 0, var isPlaying: Boolean = true) : Thread(){
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
                            binding.sbMainPlayer.progress = ((mills / playTime)*10).toInt()
                        }

                        if(mills %1000 == 0f){
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