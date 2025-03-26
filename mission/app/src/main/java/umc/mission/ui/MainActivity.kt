package umc.mission.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import umc.mission.R
import umc.mission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 여기는 바인딩 못쓰나?
       // val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
       // val navController = navHostFragment.navController
        //binding.bottomNavi.setupWithNavController(navController)
       binding.bottomNavi.setOnItemSelectedListener { item->
           when(item.itemId){
               R.id.navigation_home->{
                   supportFragmentManager.beginTransaction()
                       .replace(R.id.fragment_container, HomeFragment()).commit()
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
                   supportFragmentManager.beginTransaction()
                       .replace(R.id.fragment_container, StampFragment()).commit()
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

