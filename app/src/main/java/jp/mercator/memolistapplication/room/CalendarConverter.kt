package jp.mercator.memolistapplication.room

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun fromCalendar(value: Long?): Calendar? = value?.let { ca ->
        GregorianCalendar().also { gc ->
            gc.timeInMillis = value
        }
    }

    @TypeConverter
    fun calendarToLong(calendar: Calendar?): Long? = calendar?.timeInMillis
}