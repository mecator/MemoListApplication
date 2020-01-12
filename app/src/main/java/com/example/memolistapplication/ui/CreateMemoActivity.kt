package com.example.memolistapplication.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.memolistapplication.R
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.viewmodel.CreateMemoViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class CreateMemoActivity : AppCompatActivity() {

    companion object {
        internal const val MEMO_KEY = "memo"
        internal const val IS_UPDATE_KEY = "isUpdate"
    }

    private var isUpdate: Boolean = false
    private lateinit var createMemoViewModel: CreateMemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memo)
        createMemoViewModel = ViewModelProviders.of(this).get(CreateMemoViewModel::class.java)

        val editText = findViewById<TextInputEditText>(R.id.memo_text_field)
        findViewById<LinearLayout>(R.id.rootLayout).setOnClickListener { editText.clearFocus() }

        isUpdate = intent.getBooleanExtra(IS_UPDATE_KEY, false)
        var memo: Memo?
        val zone = ZoneId.systemDefault()
        val date = LocalDateTime.now()
        val instant = ZonedDateTime.of(date, zone).toInstant()
        val formatedDate = Date.from(instant)
        var createDate = formatedDate
        var id = 0L
        if (isUpdate) {
            val updateMemo = intent.getSerializableExtra(MEMO_KEY) as Memo
            editText.setText(updateMemo.description)
            id = updateMemo.id
            createDate = updateMemo.createDate
        }
        findViewById<MaterialButton>(R.id.save_button).setOnClickListener {


            val description = editText.text.toString()
            when (isUpdate) {
                false -> {
                    memo = Memo(
                        id = 0,
                        createDate = formatedDate,
                        updateDate = formatedDate,
                        description = description
                    )
                    createMemoViewModel.insert(memo)
                }
                true -> {
                    memo = Memo(
                        id = id,
                        createDate = createDate,
                        updateDate = formatedDate,
                        description = description
                    )
                    createMemoViewModel.update(memo)
                }
            }

            finish()
        }

    }
}