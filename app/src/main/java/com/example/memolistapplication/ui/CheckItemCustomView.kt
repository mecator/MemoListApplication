package com.example.memolistapplication.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.memolistapplication.model.memo.Contents
import kotlinx.android.synthetic.main.memo_check_list.view.*

class CheckItemCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    fun getCheckItem(): Contents.MemoContent {
        return Contents.MemoContent(
            Contents.MemoContent.ContentType.CHECK_MEMO,
            checkText.text.toString(),
            false
        )
    }
}