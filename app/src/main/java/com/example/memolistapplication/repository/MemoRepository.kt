package com.example.memolistapplication.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.room.MemoDao

class MemoRepository(private val memoDao: MemoDao) : MemoRepositoryImpl {
    val memoList: LiveData<List<Memo>> = memoDao.findAll()

    @WorkerThread
    override suspend fun getAll(): LiveData<List<Memo>> {
        return memoDao.findAll()
    }
    override suspend fun insert(memo: Memo?) {
        if (memo != null) {
            memoDao.createMemo(memo)
        }
    }


    override suspend fun update(memo: Memo?) {
        if (memo != null) {
            memoDao.updateMemo(memo)
        }
    }

    override suspend fun delete(memo: Memo) {
        memoDao.delete(memo)
    }
}