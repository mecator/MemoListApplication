package com.example.memolistapplication.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityMainBinding
import com.example.memolistapplication.room.Memo
import com.example.memolistapplication.ui.CreateMemoActivity.Companion.IS_UPDATE_KEY
import com.example.memolistapplication.ui.CreateMemoActivity.Companion.MEMO_KEY
import com.example.memolistapplication.viewmodel.MemoListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: MemoListAdapter
    private lateinit var memoList: List<Memo>
    private lateinit var memoListViewModel: MemoListViewModel
    var isDelete = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        memoList = arrayListOf()
        memoListViewModel = ViewModelProviders.of(this).get(MemoListViewModel::class.java)
        memoListViewModel.memoList.observe(this, Observer { memoList ->
            memoList.let { viewAdapter.setMemoList(it) }
        })
        binding.floatButton.apply {
            this.setOnClickListener { v ->
                openCreateMemoActivity(null)
            }
        }
        binding.memoList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewAdapter = MemoListAdapter(this, memoList, object : MemoClickListener {
            override fun onItemClick(memo: Memo) {
                openCreateMemoActivity(memo)
            }

            override fun onItemLongClick(memo: Memo): Boolean {
                 memoListViewModel.deleteMemo(memo)
//
//                val interpolator = OvershootInterpolator()
//                ViewCompat.animate(binding.floatButton).rotation(360f).withLayer().setDuration(300)
//                    .setInterpolator(interpolator).start()
//                val handler = Handler()
//                handler.postDelayed(Runnable {
//                    if (!isDelete) {
//                        binding.floatButton.setImageDrawable(getDrawable(R.drawable.ic_delete_24dp))
//                        isDelete=true
//                    }
//                }, 200)

                return true
            }
        })
        binding.memoList.adapter = viewAdapter
        binding.lifecycleOwner = this
    }

    fun openCreateMemoActivity(memo: Memo?) {
        val intent = Intent(applicationContext, CreateMemoActivity::class.java)
        intent.putExtra(MEMO_KEY, memo)
        intent.putExtra(IS_UPDATE_KEY, (memo != null))
        startActivity(intent)
        overridePendingTransition(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
    }

    interface MemoClickListener {
        fun onItemClick(memo: Memo)
        fun onItemLongClick(memo: Memo): Boolean
    }
}
