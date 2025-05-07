package com.example.flo

import android.app.Activity
import android.content.Intent
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
import com.example.flo.data.Song
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.ui.SongActivity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var binding: ActivityMainBinding
    private var gson : Gson = Gson()
    private var song = Song()

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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_look,R.id.navigation_locker
            )
        )


//        setMiniPlayer(song)
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = spf.getString("songData",null)
        Log.d(TAG, "song : ${songJson}")
        song = if(songJson.isNullOrEmpty()) Song(
            "라일락",
            "아이유",
            0,
            60,
            false,
            "iu_lilac"
        )
        else
            gson.fromJson(songJson,Song::class.java)
        setMiniPlayer(song)
    }

    private fun setMiniPlayer(song : Song){
        binding.layoutMiniplayer.setOnClickListener{
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("song",song)
            getResult.launch(intent)
        }
        binding.tvSongTitle.text = song.title
        binding.tvSongSinger.text = song.singer
        binding.sbMainPlayer.progress = (song.second*100000)/song.playTime
    }
}