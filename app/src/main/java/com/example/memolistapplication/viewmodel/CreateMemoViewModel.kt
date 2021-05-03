package com.example.memolistapplication.viewmodel

import android.app.Application
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.children
import androidx.lifecycle.AndroidViewModel
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.model.memo.Contents
import com.example.memolistapplication.repository.MemoRepository
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.ui.CheckItemCustomView
import com.example.memolistapplication.ui.CreateMemoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.coroutines.CoroutineContext

class CreateMemoViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: MemoRepository
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private lateinit var onViewModelListener: CreateMemoActivity.OnViewModelListener

    internal var isUpdate: Boolean = false
    internal lateinit var memo: Memo
    var firstMemo: Memo? = null
    private var calendar: Calendar? = null

    init {
        val memoDao = MemoApplication.database.memoDao()
        repository = MemoRepository(memoDao)
    }

    fun initialize(listener: CreateMemoActivity.OnViewModelListener) {
        onViewModelListener = listener
    }

    fun isNeedSaving(): Boolean = firstMemo?.description != getTitle() || firstMemo?.contents != getContents().first

    fun onClickPinButton() {
        memo.apply { isPin = !isPin }
        update(memo)
        onViewModelListener.onClickPinButton()
    }

    interface NotificationListener {
        fun onClick(calendar: Calendar?)
    }

    fun onClickNotificationButton() {
        onViewModelListener.onClickNotificationButton(
            object : NotificationListener {
                override fun onClick(_calendar: Calendar?) {
                    calendar = _calendar
                }
            },
            memo
        )
    }

    fun onClickSaveButton(title: String, isMemo: Boolean) {
        val contents = getContents()
        val zone = ZoneId.systemDefault()
        val date = LocalDateTime.now()
        val instant = ZonedDateTime.of(date, zone).toInstant()
        val formatedDate = Date.from(instant)
        when (isUpdate) {
            false -> {
                memo.apply {
                    createDate = formatedDate
                    updateDate = formatedDate
                    description = title.let { if (it.isBlank()) "無題のメモ" else it }
                    this.contents = contents.first
                    checkRatio = contents.second
                    this.isMemo = isMemo
                    this@CreateMemoViewModel.calendar?.also { this.calendar = it }
                }
                insert(memo)
            }
            true -> {
                memo.apply {
                    id = memo.id
                    createDate = memo.createDate
                    updateDate = formatedDate
                    description = title.let { if (it.isBlank()) "無題のメモ" else it }
                    this.contents = contents.first
                    checkRatio = contents.second
                    this.isMemo = isMemo
                    if (this@CreateMemoViewModel.calendar != null) {
                        this.calendar = this@CreateMemoViewModel.calendar
                    } else if (this.calendar != null && this@CreateMemoViewModel.calendar == null) {
                        this.calendar = null
                    }
                }
                if (firstMemo?.let { memo.isEqual(it) } == true) return

                update(memo)
            }
        }
        onViewModelListener.onClickSaveButton()
    }

    fun onClickListButton() {
        onViewModelListener.onClickListButton()
    }

    fun insert(memo: Memo?) = scope.launch(IO) {
        repository.insert(memo)
    }

    fun update(memo: Memo?) = scope.launch(IO) { repository.update(memo) }

    fun getTitle(): String = onViewModelListener.getTitle()

    fun getContents(): Pair<String, Double> {
        var countIsCheck = 0
        val list = onViewModelListener.getFrameChildren().filter { view -> view is CheckItemCustomView || view is AppCompatEditText }.map {
            val memoContents = when (it) {
                is CheckItemCustomView -> it.getCheckItem()
                is AppCompatEditText -> it.text.toString().takeIf { it.isNotEmpty() }?.let { text -> Contents.MemoContent(Contents.MemoContent.ContentType.TEXT_MEMO, text, false) }
                else -> null
            }
            memoContents?.apply { if (this.isCheck) countIsCheck += 1 }
            memoContents
        }.toList().filterNotNull()
        val ratio = countIsCheck.toDouble().div(list.size.toDouble())
        return Pair(Contents.objectToString(list), ratio)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun getNotificationInfo(): String? {
        return memo.calendar?.run {
            val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
            simpleDateFormat.format(time) + " に通知予定"
        }
    }

    fun isNotificationVisibility(): Boolean = getNotificationInfo()?.isNotBlank() == true && !memo.isPastCalendar()
}