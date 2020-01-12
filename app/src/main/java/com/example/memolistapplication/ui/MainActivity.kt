package com.example.memolistapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityMainBinding
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.viewmodel.MemoListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: MemoListAdapter
    private lateinit var memoList: List<Memo>
    private lateinit var memoListViewModel: MemoListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        memoList = arrayListOf()
        memoListViewModel = ViewModelProviders.of(this).get(MemoListViewModel::class.java)
        memoListViewModel.memoList.observe(this, Observer { memoList ->
            memoList.let { viewAdapter.setMemoList(it) }
        })
        float_button.apply {
            this.setOnClickListener { v ->
                startActivity(Intent(applicationContext, CreateMemoActivity::class.java))
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
            }
        }
        binding.memoList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewAdapter = MemoListAdapter(this, memoList)
        binding.memoList.adapter = viewAdapter
    }
}
