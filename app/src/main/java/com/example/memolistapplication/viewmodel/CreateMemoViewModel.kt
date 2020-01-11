package com.example.memolistapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.repository.MemoRepository
import com.example.memolistapplication.room.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CreateMemoViewModel(app: Application) : AndroidViewModel(app)  {
    private val repository: MemoRepository

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    init {
        val memoDao=MemoApplication.database.memoDao()
        repository= MemoRepository(memoDao)
    }
    fun insert(memo: Memo)=scope.launch (Dispatchers.IO){
        repository.insert(memo)
    }
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}