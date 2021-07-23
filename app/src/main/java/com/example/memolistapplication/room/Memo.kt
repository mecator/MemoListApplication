package com.example.memolistapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.memolistapplication.model.memo.Contents
import java.io.Serializable
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@TypeConverters(DateConverter::class, CalendarConverter::class)
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

    fun japanDate(): String {
        val df = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        return df.format(updateDate)
    }

    fun firstContent(): String {
        return Contents.stringToObject(contents ?: "").getOrNull(0)?.text ?: ""
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
                calendar == memo.calendar
                )
    }

    fun getRatioPercent(): String = ((checkRatio * 1000.0).roundToInt() / 10.0).toString()

    fun is100percent(): Boolean = (checkRatio.toFloat() == 1f)
    fun isPastCalendar(): Boolean {
        val _calendar = calendar
        _calendar ?: return true
        val today = Calendar.getInstance()
        return today > _calendar
    }

}