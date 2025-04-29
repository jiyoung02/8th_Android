package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.TwoButtonPopup.PopupClick
import com.example.myapplication.databinding.ActivityEditMemoBinding

class EditMemoActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditMemoBinding
    lateinit var  dialog : TwoButtonPopup
    private var isRestart : Boolean = false
    private val TAG = javaClass.simpleName

    companion object{
        var memo : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMemoBinding.inflate(layoutInflater)

        binding.btnSave.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
//            intent.putExtra("memo",memo)
            startActivity(intent)
        }
        dialog = TwoButtonPopup(object : PopupClick {
            override fun leftClickFunction() {
                memo = ""
                binding.etMemo.setText(memo)
                isRestart = false
            }
            override fun rightClickFunction() {
                binding.etMemo.setText(memo)
                isRestart = false
            }
        })

        setContentView(binding.root)
    }



    override fun onPause() {
        super.onPause()
        memo = binding.etMemo.text.toString()
    }

    override fun onResume() {
        super.onResume()
        if (isRestart) dialog.show(supportFragmentManager, "Popup")
        else if (memo.isNotEmpty()) binding.etMemo.setText(memo)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
        isRestart = true

    }

}