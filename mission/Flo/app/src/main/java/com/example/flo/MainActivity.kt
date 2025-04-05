package com.example.flo

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.flo.data.Song
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.ui.SongActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


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
        setMiniPlayer()
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun setMiniPlayer(){
        binding.layoutMiniplayer.setOnClickListener{
            val song = Song(
                binding.tvSongTitle.text.toString(),
                binding.tvSongSinger.text.toString()
            )
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title",song.singer)
            intent.putExtra("singer",song.title)
            startActivity(intent)
        }
    }
}