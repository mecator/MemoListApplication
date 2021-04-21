package com.example.memolistapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Memo::class],version = 5)
@TypeConverters(DateConverter::class)
abstract class DataBase :RoomDatabase(){
    abstract fun memoDao():MemoDao
}