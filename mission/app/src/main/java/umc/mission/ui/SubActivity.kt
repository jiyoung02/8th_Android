package umc.mission.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umc.mission.databinding.ActivitySubBinding

class SubActivity: AppCompatActivity() {
    lateinit var binding : ActivitySubBinding
    private var text : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        text = intent.getStringExtra("emotion")!!

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.tvSubText.text = text
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}