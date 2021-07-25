package jp.mercator.memolistapplication.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import jp.mercator.memolistapplication.model.memo.Contents
import kotlinx.android.synthetic.main.memo_check_list.view.*

class CheckItemCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    fun getCheckItem(): Contents.MemoContent? {
        val str = checkText.text.toString()
        if (str.isEmpty()) return null

        return Contents.MemoContent(
            Contents.MemoContent.ContentType.CHECK_MEMO,
            str,
            checkbox.isChecked
        )
    }

    fun setView(memo: Contents.MemoContent) {
        checkText.setText(memo.text, TextView.BufferType.EDITABLE)
        checkbox.isChecked = memo.isCheck
    }
}