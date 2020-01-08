package com.example.memolistapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memolistapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FloatingActionButton>(R.id.float_button).apply {
            this.setOnClickListener { v->Snackbar.make(v,"floatbuttonnnn!!!",Snackbar.LENGTH_LONG).show() }
        }
    }
}
