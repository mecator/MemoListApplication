package com.example.memolistapplication.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.room.MemoDao

class MemoRepository(private val memoDao: MemoDao) {
    val memoList: LiveData<List<Memo>> = memoDao.findAll()

    @WorkerThread
    suspend fun insert(memo: Memo?) {
        if (memo != null) {
            memoDao.createMemo(memo)
        }
    }
    @WorkerThread
    suspend fun update(memo: Memo?) {
        if (memo != null) {
            memoDao.updateMemo(memo)
        }
    }
}