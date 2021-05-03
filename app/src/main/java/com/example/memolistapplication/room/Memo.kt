package com.example.memolistapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

@TypeConverters(DateConverter::class,CalendarConverter::class)
@Entity
data class Memo(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "create_date")
    var createDate: Date,
    @ColumnInfo(name = "update_date")
    var updateDate: Date?,
    var description: String,
    var contents: String?,
    var isPin: Boolean = false,
    var checkRatio: Double,
    var isMemo: Boolean = true,
    var calendar: Calendar?

) : Serializable {
    companion object {
        fun defaultMemo(isMemo: Boolean): Memo {
            return Memo(0, Date(), Date(), "", "", false, 0.0, isMemo, null)
        }
    }

    fun alterMemo(): Memo {
        return Memo(id, createDate, updateDate, description, contents, isPin, checkRatio, isMemo, calendar)
    }

    fun isEqual(memo: Memo): Boolean {
        return (id == memo.id &&
                createDate == memo.createDate &&
                updateDate == memo.updateDate &&
                description == memo.description &&
                contents == memo.contents &&
                isPin == memo.isPin &&
                checkRatio == memo.checkRatio &&
                isMemo == memo.isMemo &&
                calendar==memo.calendar
                )
    }

    fun getRatioPercent(): String = (BigDecimal(checkRatio).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble() * 100).toString()

    fun is100percent(): Boolean = (checkRatio.toFloat() == 1f)
    fun isPastCalendar():Boolean{
        val _calendar=calendar
        _calendar?:return true
        val today=Calendar.getInstance()
        return today > _calendar
    }

}