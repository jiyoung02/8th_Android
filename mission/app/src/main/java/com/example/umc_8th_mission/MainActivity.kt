package umc.mission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener(){
        binding.ivHappy.setOnClickListener {
            binding.tvHappy.text = "click"
        }
    }
}

