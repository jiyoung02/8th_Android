package com.example.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.PopupTemplateTwobuttonsBinding

class TwoButtonPopup (
    private val clickFunc : PopupClick,
) : DialogFragment(){
    lateinit var binding: PopupTemplateTwobuttonsBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = PopupTemplateTwobuttonsBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        val dialog = builder.create()

        // 텍스트 바꾸기
        setOnClickListener()

        // 배경 투명 + 밝기 조절 (0.7)
        dialog.window?.let { window ->
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val layoutParams = window.attributes
            layoutParams.dimAmount = 0.7f
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.attributes = layoutParams
        }

        return dialog
    }

    private fun setOnClickListener() {
        binding.btnLeft.setOnClickListener {
            // 함수 실행
            clickFunc.leftClickFunction()
            // 모든 함수 수행 후 팝업 닫기
            dismiss()
        }
        binding.btnRight.setOnClickListener {
            // 함수 실행
            clickFunc.rightClickFunction()
            // 모든 함수 수행 후 팝업 닫기
            dismiss()
        }
    }

    interface PopupClick{
        fun leftClickFunction()
        fun rightClickFunction()
    }


}