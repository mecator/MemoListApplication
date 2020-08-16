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
    var createDate: Date,
    @ColumnInfo(name = "update_date")
    var updateDate: Date?,
    var description: String,
    var contents: String?,
    var isPin: Boolean = false

) : Serializable {
    companion object {
        fun defaultMemo(): Memo {
            return Memo(0, Date(), Date(), "", "", false)
        }
    }

    fun alterMemo(): Memo {
        return Memo(id, createDate, updateDate, description, contents, isPin)
    }

    fun isEqual(memo: Memo): Boolean {
        return (id == memo.id && createDate == memo.createDate && updateDate == memo.updateDate && description == memo.description && contents == memo.contents && isPin == memo.isPin)
    }
}