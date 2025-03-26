package umc.mission.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import umc.mission.R
import umc.mission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // splashScreen 적용
        //installSplashScreen()
        setContentView(binding.root)


        // 여기는 바인딩 못쓰나?
       // val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
       // val navController = navHostFragment.navController
        //binding.bottomNavi.setupWithNavController(navController)

        // bottomNav 설정
        binding.bottomNavi.setOnItemSelectedListener { item->
           val transaction = supportFragmentManager.beginTransaction()
           when(item.itemId){
               R.id.navigation_home->{
                    transaction.setCustomAnimations(
                        R.anim.anim_slide_in_from_left_fade_in,
                        R.anim.anim_fade_out
                    )
                    transaction.replace(R.id.fragment_container, HomeFragment()).commit()
                   //navController.navigate(R.id.navigation_home)
                   true
               }
               R.id.navigation_record->{
                   Toast.makeText(this, "아직 사용할 수 없어요!", Toast.LENGTH_SHORT).show()
                   false
               }
               R.id.navigation_social->{
                   Toast.makeText(this, "아직 사용할 수 없어요!", Toast.LENGTH_SHORT).show()
                   false
               }
               R.id.navigation_mypage->{
                   //navController.navigate(R.id.navigation_mypage)
                   transaction.setCustomAnimations(
                       R.anim.anim_slide_in_from_right_fade_in,
                       R.anim.anim_fade_out
                   )
                   transaction.replace(R.id.fragment_container, StampFragment()).commit()
                   true
               }
               else -> false
           }
       }

    }
    fun hideBottomNavigation(state:Boolean){
        if(state) binding.bottomNavi.visibility = View.GONE else binding.bottomNavi.visibility=View.VISIBLE
    }

}

