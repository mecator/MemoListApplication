package com.example.memolistapplication.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.memolistapplication.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CreateMemoActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_memo)
        val editText=findViewById<TextInputEditText>(R.id.memo_text_field)
        findViewById<LinearLayout>(R.id.rootLayout).setOnClickListener {editText.clearFocus()  }
        findViewById<MaterialButton>(R.id.save_button).setOnClickListener { finish() }
    }
}