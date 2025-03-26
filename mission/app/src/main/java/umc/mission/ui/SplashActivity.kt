package umc.mission.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import umc.mission.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateStatusBarColor()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500) // 1.5초 딜레이
    }

    private fun updateStatusBarColor() {
        val window = this.window
        window.statusBarColor = Color.WHITE

    }
}