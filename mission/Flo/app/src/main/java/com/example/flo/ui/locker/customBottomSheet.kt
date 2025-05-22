package com.example.flo.ui.locker

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flo.databinding.BottomsheetSelectAllBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class customBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding : BottomsheetSelectAllBinding
    var listener: BottomSheetListener? = null

    override fun getTheme(): Int  = com.google.android.material.R.style.Theme_Design_BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetSelectAllBinding.inflate(inflater, container, false)
        binding.layoutDelete.setOnClickListener {
            Log.d("test","clickdelete")
            listener?.deleteItem()
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.isDraggable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener!!.dismissSheet()
    }

    interface  BottomSheetListener {
        fun deleteItem()
        fun dismissSheet()
    }
}