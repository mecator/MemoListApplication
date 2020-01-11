package com.example.memolistapplication.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface MemoDao {
    @Insert(onConflict = REPLACE)
    fun createMemo(memo: Memo): Long

    @Query("SELECT * FROM Memo")
    fun findAll(): List<Memo>

    @Update
    fun updateMemo(memo: Memo)

    @Delete
    fun delete(memo: Memo)
}