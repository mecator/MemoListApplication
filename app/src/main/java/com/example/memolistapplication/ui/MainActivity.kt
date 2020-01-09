package com.example.memolistapplication.ui

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
                //Snackbar.make(v,"floatbuttonnnn!!!",Snackbar.LENGTH_LONG).show()
                val createMemoFragment = CreateMemoFragment.createInstance()
                val transaction=supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragment_frame,createMemoFragment).commit()
                v.visibility=GONE
            }
        }
    }
}
