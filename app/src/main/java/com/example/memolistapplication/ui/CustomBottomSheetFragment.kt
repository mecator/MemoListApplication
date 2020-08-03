package com.example.memolistapplication.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.children
import com.example.memolistapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.checkList

class CustomBottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        fun newInstance(): CustomBottomSheetFragment {
            return CustomBottomSheetFragment()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val root = View.inflate(context, R.layout.fragment_bottom_sheet, null)
        dialog.setContentView(root)

        root.addButton.setOnClickListener {
            root.checkList.addView(LayoutInflater.from(context).inflate(R.layout.memo_check_list, null, false))
        }
        root.createButton.setOnClickListener {
            val checkList = root.checkList.children.filter { view -> view is CheckItemCustomView }.map {
                val view = it as? CheckItemCustomView
                view?.getCheckItem()
            }.toList().filterNotNull()
        }
    }
}