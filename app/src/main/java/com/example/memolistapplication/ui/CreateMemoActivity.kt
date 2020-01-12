package com.example.memolistapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityCreateMemoBinding
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.viewmodel.CreateMemoViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class CreateMemoActivity : AppCompatActivity() {

    companion object {
        internal const val MEMO_KEY = "memo"
        internal const val IS_UPDATE_KEY = "isUpdate"
    }

    private var isUpdate: Boolean = false
    private lateinit var createMemoViewModel: CreateMemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCreateMemoBinding>(
            this,
            R.layout.activity_create_memo
        )

        createMemoViewModel = ViewModelProviders.of(this).get(CreateMemoViewModel::class.java)
        createMemoViewModel.finishActivity.observe(this,androidx.lifecycle.Observer {
            finish()
        })

        isUpdate = intent.getBooleanExtra(IS_UPDATE_KEY, false)
        createMemoViewModel.also {
            it.isUpdate = isUpdate
            it.memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else Memo(0L,Date(),Date(),"")
        }
        binding.viewModel=createMemoViewModel
        binding.lifecycleOwner=this
        binding.memo=if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else null
    }
}