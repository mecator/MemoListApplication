package com.example.memolistapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.repository.MemoRepository
import com.example.memolistapplication.room.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class MemoListViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: MemoRepository
    val memoList: LiveData<List<Memo>>

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val memoDao = MemoApplication.database.memoDao()
        repository = MemoRepository(memoDao)
        memoList = repository.memoList
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}