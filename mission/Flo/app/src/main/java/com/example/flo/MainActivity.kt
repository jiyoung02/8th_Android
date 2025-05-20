package com.example.flo

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
    lateinit var spf : SharedPreferences
    private var gson : Gson = Gson()
    private var song = Song()
    lateinit var songDB : SongDatabase

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

        val songDB = SongDatabase.getIntance(this)!!
        song = if(songId == 0) songDB.songDao().getSong(1) else songDB.songDao().getSong(songId)
        Log.d(TAG, "song : ${song}")
        setMiniPlayer(song)
    }

    fun setMiniPlayer(song : Song){
        binding.layoutMiniplayer.setOnClickListener{
            spf.edit().putInt("songId",song.id).apply()
            val intent = Intent(this,SongActivity::class.java)
            getResult.launch(intent)
        }
        binding.tvSongTitle.text = song.title
        binding.tvSongSinger.text = song.singer
        binding.sbMainPlayer.progress = (song.second*100000)/song.playTime
    }

    private fun initDummyAlbum(){
        val albums = songDB.albumDao().getAlbums()
        if(albums.isNotEmpty()) return
        songDB.albumDao().insert(Album("Lilac","아이유",R.drawable.img_album_exp2))
        songDB.albumDao().insert( Album("IVE EMPATHY","아이브", R.drawable.img_album1))
        songDB.albumDao().insert( Album("Whiplash","에스파",R.drawable.img_album2))
        songDB.albumDao().insert(Album("Butter","방탄소년단",R.drawable.img_album_exp))
        songDB.albumDao().insert( Album("Lilac","아이유",R.drawable.img_album_exp2))
        songDB.albumDao().insert( Album("IVE EMPATHY","아이브", R.drawable.img_album1))

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

}