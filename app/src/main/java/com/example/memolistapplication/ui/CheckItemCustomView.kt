package com.example.memolistapplication.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.memolistapplication.R
import com.example.memolistapplication.model.CheckItem
import kotlinx.android.synthetic.main.memo_check_list.view.*
import java.util.zip.Inflater

class CheckItemCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    fun getCheckItem(): CheckItem {
        return CheckItem(
            checkText.text.toString(), false
        )
    }
}