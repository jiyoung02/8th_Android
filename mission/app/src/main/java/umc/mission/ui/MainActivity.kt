package umc.mission.ui

import android.content.Intent
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
            startSubActivity(binding.tvHappy.text.toString())
        }
    }

    private fun startSubActivity(text : String){
        val intent = Intent(this, SubActivity::class.java)
        intent.putExtra("emotion", text)
        startActivity(intent)
    }

}

