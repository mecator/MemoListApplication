package com.example.memolistapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.repository.MemoRepository
import com.example.memolistapplication.repository.MemoRepositoryImpl
import com.example.memolistapplication.room.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MemoListViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: MemoRepositoryImpl
    var memoList: LiveData<List<Memo>>
    private var _deleteMemoState = MutableLiveData<String>()
    val deleteMemoState: LiveData<String>
        get() = _deleteMemoState
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

    fun deleteMemo(memo: Memo) {
        var isSuccess = true
        scope.launch(Dispatchers.IO) {
            runCatching { repository.delete(memo) }.fold(
                onFailure = { isSuccess = true },
                onSuccess = { isSuccess = false })
        }
        if (isSuccess) {
            onDeleteMemoSuccess()
        } else {
            onDeleteMemoFailure()
        }
    }

    private fun onDeleteMemoFailure() {
        _deleteMemoState.value = "メモの消去に失敗しました"
    }

    private fun onDeleteMemoSuccess() {
        _deleteMemoState.value = "メモの消去に成功しました"
    }
}