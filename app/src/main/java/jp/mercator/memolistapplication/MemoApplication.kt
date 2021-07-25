package jp.mercator.memolistapplication

import android.app.Application
import androidx.room.Room
import jp.mercator.memolistapplication.room.DataBase

class MemoApplication : Application() {
    companion object {
        lateinit var database: DataBase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, objectOf<DataBase>(), "memo.db").fallbackToDestructiveMigration().build()
    }
}

internal inline fun <reified T : Any> objectOf() = T::class.java