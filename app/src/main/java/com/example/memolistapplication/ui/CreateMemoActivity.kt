package com.example.memolistapplication.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityCreateMemoBinding
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.viewmodel.CreateMemoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class CreateMemoActivity : AppCompatActivity() {

    companion object {
        internal const val MEMO_KEY = "memo"
        internal const val IS_UPDATE_KEY = "isUpdate"
    }

    private var isUpdate: Boolean = false
    private lateinit var createMemoViewModel: CreateMemoViewModel
    private lateinit var binding: ActivityCreateMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_create_memo
        )

        createMemoViewModel = ViewModelProviders.of(this).get(CreateMemoViewModel::class.java)
            .apply {
                initialize(this@CreateMemoActivity, object : OnSaveClickListener {
                    override fun onClick() {
                        finish()
                    }
                })
            }

        isUpdate = intent.getBooleanExtra(IS_UPDATE_KEY, false)
        createMemoViewModel.also {
            it.isUpdate = isUpdate
            it.memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else Memo.defaultMemo()
        }

        binding.apply {
            viewModel = createMemoViewModel
            lifecycleOwner = this@CreateMemoActivity
            memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else null
        }
    }

    interface OnSaveClickListener {
        fun onClick()
    }

    override fun onBackPressed() {
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

    fun saveMemo() {
        binding.also {
            createMemoViewModel.onClickSaveButton(it.memoTitle.text.toString(), it.memoContents.text.toString())
        }
    }
}