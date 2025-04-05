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

    // 아직 기능이 뭐가 나올지 몰라서 하드 코딩으로 각각 설정
    // 비슷한 기능을 추가하게 될 경우 수정 예정
    private fun setClickListener(){
        binding.ivHappy.setOnClickListener {
            startSubActivity(binding.tvHappy.text.toString())
        }
        binding.ivExcited.setOnClickListener {
            startSubActivity(binding.tvExcited.text.toString())
        }
        binding.ivNormal.setOnClickListener {
            startSubActivity(binding.tvNormal.text.toString())
        }
        binding.ivNervous.setOnClickListener {
            startSubActivity(binding.tvNervous.text.toString())
        }
        binding.ivAngry.setOnClickListener {
            startSubActivity(binding.tvAngry.text.toString())
        }
    }

    private fun startSubActivity(text : String){
        val intent = Intent(this, SubActivity::class.java)
        intent.putExtra("emotion", text)
        startActivity(intent)
    }

}

