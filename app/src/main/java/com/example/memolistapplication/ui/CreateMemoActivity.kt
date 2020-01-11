package com.example.memolistapplication.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.R
import com.example.memolistapplication.room.Memo
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class CreateMemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memo)
        val editText = findViewById<TextInputEditText>(R.id.memo_text_field)
        findViewById<LinearLayout>(R.id.rootLayout).setOnClickListener { editText.clearFocus() }
        findViewById<MaterialButton>(R.id.save_button).setOnClickListener {
            val zone = ZoneId.systemDefault()

            val date = LocalDateTime.now()
            val instant = ZonedDateTime.of(date, zone).toInstant()
            val formatedDate = Date.from(instant)
            var memo = Memo(
                id = 0,
                createDate = formatedDate,
                updateDate = formatedDate,
                description = editText.text.toString()
            )
            val dao = MemoApplication.database.memoDao()
            GlobalScope.launch(Dispatchers.Default) {
                dao.createMemo(memo)

            }
            finish()
        }

    }
}