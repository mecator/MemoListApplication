package com.example.memolistapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.util.*
@TypeConverters(DateConverter::class)
@Entity
data class Memo(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "create_date")
    var createDate:Date,
    @ColumnInfo(name = "update_date")
    var updateDate: Date,
    var description:String

):Serializable