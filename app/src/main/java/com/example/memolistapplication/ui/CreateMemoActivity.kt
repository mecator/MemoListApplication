package com.example.memolistapplication.ui

import android.app.*
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Intent
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.memolistapplication.AlarmBroadcastReceiver
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityCreateMemoBinding
import com.example.memolistapplication.model.memo.Contents
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.viewmodel.CreateMemoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class CreateMemoActivity : AppCompatActivity() {

    companion object {
        internal const val MEMO_KEY = "memo"
        internal const val IS_UPDATE_KEY = "isUpdate"
        internal const val IS_MEMO_KEY = "isMemo"
    }

    private lateinit var memo: Memo
    private var isUpdate: Boolean = false
    private var isMemo: Boolean = true
    private var calendar: Calendar? = null
    private lateinit var createMemoViewModel: CreateMemoViewModel
    private lateinit var binding: ActivityCreateMemoBinding
    fun setAlarm(calendar: Calendar?) {
        calendar ?: return
        val intent = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        val pending = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
        val am = applicationContext.getSystemService(AlarmManager::class.java)
        am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pending)
        Toast.makeText(applicationContext, "set alarm", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_create_memo
        )
        isUpdate = intent.getBooleanExtra(IS_UPDATE_KEY, false)
        isMemo = intent.getBooleanExtra(IS_MEMO_KEY, true)
        memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else Memo.defaultMemo(isMemo)

        createMemoViewModel = ViewModelProviders.of(this).get(CreateMemoViewModel::class.java)
            .apply {
                initialize(object : OnViewModelListener {
                    override fun getFrameChildren(): List<View> {
                        return binding.frame.children.toList()
                    }

                    override fun onClickSaveButton() {
                        finish()
                        setAlarm(calendar)
                    }

                    override fun onClickListButton() {
                        binding.frame.addView(LayoutInflater.from(baseContext).inflate(R.layout.memo_check_list, null, false))
                    }

                    override fun onClickPinButton() {
                        val color = if (memo.isPin) ContextCompat.getColorStateList(baseContext, R.color.colorAccent) else ContextCompat.getColorStateList(baseContext, R.color.icon_black)
                        color?.also { binding.pinButton.backgroundTintList = color }
                    }

                    override fun onClickNotificationButton(listener: CreateMemoViewModel.NotificationListener) {
                        DatePickFragment { _, year, month, dayOfMonth ->
                            TimePickFragment { _, hour, minute ->
                                val _calendar = Calendar.getInstance()
                                _calendar.set(year, month, dayOfMonth, hour, minute)
                                calendar = _calendar
                                listener.onClick(_calendar)
                            }.show(supportFragmentManager, "timePicker")

                        }.show(supportFragmentManager, "datePicker")
                    }

                    override fun getTitle(): String = binding.memoTitle.text.toString()

                })
            }

        createMemoViewModel.also { model ->
            model.isUpdate = isUpdate
            memo.also {
                model.memo = it
                model.firstMemo = it.alterMemo()
            }
        }

        binding.apply {
            viewModel = createMemoViewModel
            lifecycleOwner = this@CreateMemoActivity
            memo = if (isUpdate) intent.getSerializableExtra(MEMO_KEY) as Memo else Memo.defaultMemo(isMemo)
            setMemoView()
            if (isMemo) {
                listButton.visibility = GONE
            }
        }

    }

    interface OnViewModelListener {
        fun getFrameChildren(): List<View>
        fun onClickSaveButton()
        fun onClickListButton()
        fun onClickPinButton()
        fun onClickNotificationButton(listener: CreateMemoViewModel.NotificationListener)
        fun getTitle(): String
    }

    override fun onBackPressed() {
        if (!createMemoViewModel.isNeedSaving()) finish() else {
            MaterialAlertDialogBuilder(this).apply {
                title = "確認"
                setMessage("編集内容が保存されていません")
                setNeutralButton("キャンセル") { dialog, _ ->
                    dialog.dismiss()
                }
                setNegativeButton("保存しない") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                setPositiveButton("保存する") { dialog, _ ->
                    dialog.dismiss()
                    saveMemo()
                }
                create()
                show()
            }
        }
    }

    private fun setMemoView() {
        binding.also { b ->
            if (!isUpdate && isMemo) binding.frame.addView(layoutInflater.inflate(R.layout.memo_text, null).findViewById(R.id.memoText))
            if (!isUpdate && !isMemo) binding.frame.addView(LayoutInflater.from(baseContext).inflate(R.layout.memo_check_list, null, false) as CheckItemCustomView)

            if (!isUpdate) b.memoTitle.requestFocus()
            val list = memo.contents?.let { Contents.stringToObject(it) }
            val pinColor = if (memo.isPin) ContextCompat.getColorStateList(this, R.color.colorAccent) else ContextCompat.getColorStateList(this, R.color.icon_black)
            pinColor?.also { b.pinButton.backgroundTintList = it }
            val notifyColor = if (memo.calendar != null) ContextCompat.getColorStateList(this, R.color.colorAccent) else ContextCompat.getColorStateList(this, R.color.icon_black)
            notifyColor?.also { b.notificationButton.backgroundTintList = notifyColor }
            list?.forEach {
                when (it.type) {
                    Contents.MemoContent.ContentType.TEXT_MEMO -> {
                        val v = layoutInflater.inflate(R.layout.memo_text, null)
                        v.findViewById<EditText>(R.id.memoText).setText(it.text, TextView.BufferType.EDITABLE)
                        b.frame.addView(v)
                    }
                    Contents.MemoContent.ContentType.CHECK_MEMO -> {
                        val v = LayoutInflater.from(baseContext).inflate(R.layout.memo_check_list, null, false) as CheckItemCustomView
                        v.setView(it)
                        b.frame.addView(v)
                    }
                }
            }
        }
    }

    fun saveMemo() {
        binding.also {
            createMemoViewModel.onClickSaveButton(it.memoTitle.text.toString(), isMemo)
        }
    }

    class HapticTouchListener() : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val isHandled = v.onTouchEvent(event)
            if (!v.isHapticFeedbackEnabled) return isHandled

            if (isHandled) {
                when (event.actionMasked) {
                    // 押したとき
                    MotionEvent.ACTION_DOWN -> {
                        v.performHapticFeedback(
                            HapticFeedbackConstants.VIRTUAL_KEY
                        )
                    }
                    // 離したとき
                    MotionEvent.ACTION_UP -> {
                        // VIRTUAL_KEY_RELEASEはAPI Level 27以上で使用可能なので、
                        // 必要に応じて条件分岐を行う
                        v.performHapticFeedback(
                            HapticFeedbackConstants.VIRTUAL_KEY_RELEASE
                        )
                    }
                }
            }

            return isHandled
        }
    }

}