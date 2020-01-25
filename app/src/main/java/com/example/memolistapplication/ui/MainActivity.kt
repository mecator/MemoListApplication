package com.example.memolistapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var isFabPlusIcon: Boolean = true
    private var isDeleteMode: Boolean = false
    private lateinit var viewAdapter: MemoListAdapter
    private lateinit var memos: List<Memo>
    private lateinit var memoListViewModel: MemoListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        memos = arrayListOf()
        memoListViewModel = ViewModelProviders.of(this).get(MemoListViewModel::class.java)
        memoListViewModel.memoList.observe(this, Observer { memoList ->
            memoList.let { viewAdapter.setMemoList(it) }
        })
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                floatButton.apply {
                    this.setOnClickListener { v ->
                        openCreateMemoActivity(null)
                    }
                }
                memoList.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                viewAdapter =
                    MemoListAdapter(this@MainActivity, memos, object : MemoClickListener {
                        override fun onItemClick(view: View, memo: Memo) {
                            if (!isDeleteMode) {
                                openCreateMemoActivity(memo)
                            } else {
                                changeCarColor(view)
                            }
                        }

                        override fun onItemLongClick(view: View, memo: Memo): Boolean {
                            isDeleteMode = true
                            //memoListViewModel.deleteMemo(memo)
                            changeCarColor(view)
                            rotateFAB(floatButton)
                            return true
                        }
                    })
                memoList.adapter = viewAdapter
                lifecycleOwner = this@MainActivity
            }
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

    fun changeCarColor(card: View) {

        if (card is MaterialCardView) {
            card.setCardBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.colorAccent
                )
            )
        }
    }

    fun rotateFAB(fab: View) {
        if (fab is FloatingActionButton) {
            if (!isDeleteMode)
                ViewCompat.animate(fab).rotation(360f).withLayer().setDuration(300L).setInterpolator(
                    OvershootInterpolator()
                )
            if (isFabPlusIcon) {
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_delete_24dp))
                isFabPlusIcon = false
            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add))
                isFabPlusIcon = true
            }
        }
    }

    interface MemoClickListener {
        fun onItemClick(view: View, memo: Memo)
        fun onItemLongClick(view: View, memo: Memo): Boolean
    }
    interface ViewModelListener{
        fun onDeleteMemoFailure()
    }
}
