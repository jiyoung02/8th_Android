package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val a = A()
        val b = B()

        a.start()
        a.join() // a 실행시 다른 스레드가 실행하지 못하도록 막기 위함

        b.start()

    }

    class  A : Thread(){
        override fun run() {
            super.run()
            for(i in 1..1000){
                Log.d("test", "first : $i")
            }
        }
    }
    class  B : Thread(){
        override fun run() {
            super.run()
            for(i in 1000 downTo 1){
                Log.d("test", "second : $i")
            }
        }
    }
}

