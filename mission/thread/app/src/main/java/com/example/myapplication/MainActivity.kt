package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //threadBasic()

        val timer : Timer = Timer()
        var isPlaying : Boolean = false
        timer.start()

        binding.btnStart.setOnClickListener {
            isPlaying = !isPlaying
            binding.btnStart.text = if(isPlaying) "Pause" else "Start"
            timer.isPlaying = isPlaying
            Log.d(TAG,"start ${isPlaying}")
        }

        binding.btnClear.setOnClickListener {
            timer.clear()
        }
    }

    @SuppressLint("DefaultLocale")
    inner class Timer(var  isPlaying : Boolean = false) : Thread(){
        private var sec : Int = 0
        private var min : Int = 0
        private var mill : Int = 0
        override fun run() {
            super.run()
            while (true){
                if (isPlaying){
                    sleep(10)
                    mill++
                    if(mill >= 100) {
                        mill %= 100
                        sec ++
                    }
                    if(sec >= 60){
                        sec %= 60
                        min++
                    }
                    runOnUiThread {
                        binding.tvTime.text = String.format("%02d : %02d.%02d", min, sec, mill )
                    }
                }
            }

        }
        fun clear(){
            sec = 0
            min = 0
            mill = 0
            runOnUiThread {
                binding.tvTime.text = String.format("%02d : %02d.%02d", min, sec, mill )
            }
        }
    }

    private fun threadBasic(){
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

