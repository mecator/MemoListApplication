package com.example.memolistapplication.extention

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.memolistapplication.ui.CreateMemoActivity

object BindingAdapter {
    @BindingAdapter("android:hapticFeedback")
    @JvmStatic
    fun setHapticFeedBack(v: View, isApplied: Boolean) {
        v.isHapticFeedbackEnabled = isApplied
        v.setOnTouchListener(CreateMemoActivity.HapticTouchListener())
    }
}