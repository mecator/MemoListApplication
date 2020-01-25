package com.example.memolistapplication.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.repository.MemoRepository
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.ui.CreateMemoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.coroutines.CoroutineContext

class CreateMemoViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: MemoRepository
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private  lateinit var mContext: Context
    private lateinit var mOnSaveClickListener:CreateMemoActivity.OnSaveClickListener
    internal var isUpdate: Boolean = false
    internal var memo: Memo = Memo(0, Date(), Date(), "")

    init {
        val memoDao = MemoApplication.database.memoDao()
        repository = MemoRepository(memoDao)
    }
    fun initialize(context:Context,listener: CreateMemoActivity.OnSaveClickListener){
        mContext=context
        mOnSaveClickListener=listener
    }

    fun onClickSaveButton(str: String) {
        val zone = ZoneId.systemDefault()
        val date = LocalDateTime.now()
        val instant = ZonedDateTime.of(date, zone).toInstant()
        val formatedDate = Date.from(instant)
        when (isUpdate) {
            false -> {
                memo.apply {
                    createDate = formatedDate
                    updateDate = formatedDate
                    description = str
                }
                insert(memo)
            }
            true -> {
                memo.apply {
                    id = memo.id
                    createDate = memo.createDate
                    updateDate = formatedDate
                    description = str
                }
                update(memo)
            }
        }
        mOnSaveClickListener.onClick()
    }

    fun insert(memo: Memo?) = scope.launch(IO) {
        repository.insert(memo)
    }

    fun update(memo: Memo?) = scope.launch(IO) { repository.update(memo) }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}