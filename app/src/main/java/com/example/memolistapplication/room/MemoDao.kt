package com.example.memolistapplication.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface MemoDao {
    @Insert(onConflict = REPLACE)
    fun createMemo(memo: Memo): Long

    @Query("SELECT * FROM Memo ORDER BY Memo.update_date DESC")
    fun findAll(): LiveData<List<Memo>>

    @Update
    fun updateMemo(memo: Memo)

    @Delete
    fun delete(memo: Memo)
}