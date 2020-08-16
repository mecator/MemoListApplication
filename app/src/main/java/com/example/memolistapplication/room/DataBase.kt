package com.example.memolistapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Memo::class],version = 4)
@TypeConverters(DateConverter::class)
abstract class DataBase :RoomDatabase(){
    abstract fun memoDao():MemoDao
}