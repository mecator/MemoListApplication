package com.example.memolistapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.repository.MemoRepository
import com.example.memolistapplication.room.Memo
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
    internal var isUpdate: Boolean = false
     var finishActivity= MutableLiveData<Boolean>()
    internal var memo: Memo = Memo(0, Date(), Date(), "")

    init {
        val memoDao = MemoApplication.database.memoDao()
        repository = MemoRepository(memoDao)
       // finishActivity.value=false
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
        finishActivity.postValue(true)
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