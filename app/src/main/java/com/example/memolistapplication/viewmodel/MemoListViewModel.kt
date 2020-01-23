package com.example.memolistapplication.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.repository.MemoRepository
import com.example.memolistapplication.repository.MemoRepositoryImpl
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MemoListViewModel(app: Context) : ViewModel() {
    private val repository: MemoRepositoryImpl
    var memoList: LiveData<List<Memo>>

    private var mListener: MainActivity.ViewModelListener? = null
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

    fun onSetListener(listener: MainActivity.ViewModelListener) {
        mListener = listener
    }

    fun deleteMemo(memo: Memo) {
        scope.launch(Dispatchers.IO) {
            runCatching { repository.delete(memo) }.fold(
                onFailure = { onDeleteMemoFailure() },
                onSuccess = {})
        }
    }

    fun onDeleteMemoFailure() {

    }
}