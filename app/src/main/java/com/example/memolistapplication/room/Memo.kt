package com.example.memolistapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*
@TypeConverters(DateConverter::class)
@Entity
data class Memo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "create_date")
    val createDate:Date,
    @ColumnInfo(name = "update_date")
    val updateDate: Date,
    val description:String

)