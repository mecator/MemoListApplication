package com.example.memolistapplication.repository

import com.example.memolistapplication.room.Memo

/**
 *
 * @author   tasugiya
 * @version  1.0
 * @since    1.0 2020/01/23 16:30.
 */
interface MemoRepositoryImpl {
    suspend fun insert(memo: Memo?)
    suspend fun update(memo: Memo?)
    suspend fun delete(memo: Memo)
}