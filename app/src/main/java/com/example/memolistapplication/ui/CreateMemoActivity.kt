package com.example.memolistapplication.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityCreateMemoBinding
import com.example.memolistapplication.model.memo.Contents
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.viewmodel.CreateMemoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreateMemoActivity : AppCompatActivity() {

    companion object {
        internal const val MEMO_KEY = "memo"
        internal const val IS_UPDATE_KEY = "isUpdate"
    }

    private var memo: Memo = Memo.defaultMemo()
    private var isUpdate: Boolean = false
    private lateinit var createMemoViewModel: CreateMemoViewModel
    private lateinit var binding: ActivityCreateMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_create_memo
        )
        isUpdate = intent.getBooleanExtra(IS_UPDATE_KEY, false)
        memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else Memo.defaultMemo()

        createMemoViewModel = ViewModelProviders.of(this).get(CreateMemoViewModel::class.java)
            .apply {
                initialize(object : OnViewModelListener {
                    override fun getFrameChildren(): List<View> {
                        return binding.frame.children.toList()
                    }

                    override fun onClickSaveButton() {
                        finish()
                    }

                    override fun onClickListButton() {
                        binding.frame.addView(LayoutInflater.from(baseContext).inflate(R.layout.memo_check_list, null, false))
                    }

                    override fun onClickTextButton() {
                        binding.frame.addView(layoutInflater.inflate(R.layout.memo_text, null).findViewById(R.id.memoText))
                        binding.frame.invalidate()
                    }

                    override fun onClickPinButton() {
                        val color = if (memo.isPin) ContextCompat.getColorStateList(baseContext, R.color.colorAccent) else ContextCompat.getColorStateList(baseContext, R.color.icon_black)
                        color?.also { binding.pinButton.backgroundTintList = color }
                    }

                    override fun getTitle(): String = binding.memoTitle.text.toString()

                })
            }


        createMemoViewModel.also { model ->
            model.isUpdate = isUpdate
            memo.also {
                model.memo = it
                model.firstMemo = it.alterMemo()
            }
        }

        binding.apply {
            viewModel = createMemoViewModel
            lifecycleOwner = this@CreateMemoActivity
            memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else Memo.defaultMemo()
            setMemoView()
        }
    }

    interface OnViewModelListener {
        fun getFrameChildren(): List<View>
        fun onClickSaveButton()
        fun onClickListButton()
        fun onClickTextButton()
        fun onClickPinButton()
        fun getTitle(): String
    }

    override fun onBackPressed() {
        if (!createMemoViewModel.isNeedSaving()) finish() else {
            MaterialAlertDialogBuilder(this).apply {
                title = "確認"
                setMessage("編集内容が保存されていません")
                setNeutralButton("キャンセル") { dialog, _ ->
                    dialog.dismiss()
                }
                setNegativeButton("保存しない") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                setPositiveButton("保存する") { dialog, _ ->
                    dialog.dismiss()
                    saveMemo()
                }
                create()
                show()
            }
        }
    }

    private fun setMemoView() {
        binding.also { b ->
            val list = memo.contents?.let { Contents.stringToObject(it) }
            val color = if (memo.isPin) ContextCompat.getColorStateList(this, R.color.colorAccent) else ContextCompat.getColorStateList(this, R.color.icon_black)
            color?.also { b.pinButton.backgroundTintList = color }
            list?.forEach {
                when (it.type) {
                    Contents.MemoContent.ContentType.TEXT_MEMO -> {
                        val v = layoutInflater.inflate(R.layout.memo_text, null)
                        v.findViewById<EditText>(R.id.memoText).setText(it.text, TextView.BufferType.EDITABLE)
                        b.frame.addView(v)
                    }
                    Contents.MemoContent.ContentType.CHECK_MEMO -> {
                        val v = LayoutInflater.from(baseContext).inflate(R.layout.memo_check_list, null, false) as CheckItemCustomView
                        v.setView(it)
                        b.frame.addView(v)
                    }
                }
            }
        }
    }

    fun saveMemo() {
        binding.also {
            createMemoViewModel.onClickSaveButton(it.memoTitle.text.toString())
        }
    }

    class HapticTouchListener() : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val isHandled = v.onTouchEvent(event)
            if (!v.isHapticFeedbackEnabled) return isHandled

            if (isHandled) {
                when (event.actionMasked) {
                    // 押したとき
                    MotionEvent.ACTION_DOWN -> {
                        v.performHapticFeedback(
                            HapticFeedbackConstants.VIRTUAL_KEY
                        )
                    }
                    // 離したとき
                    MotionEvent.ACTION_UP -> {
                        // VIRTUAL_KEY_RELEASEはAPI Level 27以上で使用可能なので、
                        // 必要に応じて条件分岐を行う
                        v.performHapticFeedback(
                            HapticFeedbackConstants.VIRTUAL_KEY_RELEASE
                        )
                    }
                }
            }

            return isHandled
        }
    }

}