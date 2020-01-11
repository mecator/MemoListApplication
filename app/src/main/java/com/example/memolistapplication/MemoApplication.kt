package com.example.memolistapplication

import android.app.Application
import androidx.room.Room
import com.example.memolistapplication.room.DataBase

class MemoApplication : Application() {
    companion object {
        lateinit var database: DataBase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, objectOf<DataBase>(), "memo.db").build()
    }
}

internal inline fun <reified T : Any> objectOf() = T::class.java