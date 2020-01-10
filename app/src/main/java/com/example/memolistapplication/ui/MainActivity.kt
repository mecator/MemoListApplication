package com.example.memolistapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.example.memolistapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FloatingActionButton>(R.id.float_button).apply {
            this.setOnClickListener { v ->
                startActivity(Intent(applicationContext,CreateMemoActivity::class.java))
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
            }
        }
    }
}
