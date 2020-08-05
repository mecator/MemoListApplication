package com.example.memolistapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityCreateMemoBinding
import com.example.memolistapplication.model.memo.Contents
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.viewmodel.CreateMemoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
                initialize(object : OnViewModelClickListener {
                    override fun onClickSaveButton() {
                        finish()
                    }

                    override fun onClickListButton() {
                        binding.frame.addView(LayoutInflater.from(applicationContext).inflate(R.layout.memo_check_list, null, false))
                    }

                    override fun onClickTextButton() {
                        binding.frame.addView(layoutInflater.inflate(R.layout.memo_text, null).findViewById(R.id.memoText))
                        binding.frame.invalidate()
                    }
                })
            }

        isUpdate = intent.getBooleanExtra(IS_UPDATE_KEY, false)
        createMemoViewModel.also {
            it.isUpdate = isUpdate
            it.memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else Memo.defaultMemo()
        }

        binding.apply {
            binding.testButton.setOnClickListener {
                val list=frame.children.filter { view -> view is CheckItemCustomView }.map {
                    val view = it as? CheckItemCustomView
                    view?.getCheckItem()
                }.toList().filterNotNull()
                val json=list[0].toString()
                val str=Contents.MemoContent.stringToObject(json)
                val t=str.type
                Log.i("qqqq",t.type)
            }

            viewModel = createMemoViewModel
            lifecycleOwner = this@CreateMemoActivity
            memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else null
        }
    }

    interface OnViewModelClickListener {
        fun onClickSaveButton()
        fun onClickListButton()
        fun onClickTextButton()
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
            createMemoViewModel.onClickSaveButton(it.memoTitle.text.toString(), " it.memoContents.text.toString()")
        }
    }
}