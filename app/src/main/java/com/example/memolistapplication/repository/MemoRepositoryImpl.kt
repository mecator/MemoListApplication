package com.example.memolistapplication.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.memolistapplication.room.Memo
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author   tasugiya
 * @version  1.0
 * @since    1.0 2020/01/23 16:30.
 */
interface MemoRepositoryImpl {
    @WorkerThread
    suspend fun getAll(): LiveData<List<Memo>>
    suspend fun insert(memo: Memo?)
    suspend fun update(memo: Memo?)
    suspend fun delete(memo: Memo)
}