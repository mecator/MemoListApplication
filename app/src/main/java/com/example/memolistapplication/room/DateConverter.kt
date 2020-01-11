package com.example.memolistapplication.room

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? = date?.let { it.time }
}