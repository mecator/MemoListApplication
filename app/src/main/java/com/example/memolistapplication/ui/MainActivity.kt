package com.example.memolistapplication.ui

import android.content.Intent
import android.content.res.ColorStateList
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isFabPlusIcon: Boolean = true
    private var isDeleteMode: Boolean = false
    private lateinit var viewAdapter: MemoListAdapter
    private lateinit var memos: List<Memo>
    private var mDeleteMemos: MutableList<Memo> = arrayListOf()
    private var mDeleteMemoViews: MutableList<View> = arrayListOf()
    private lateinit var memoListViewModel: MemoListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memos = arrayListOf()
        memoListViewModel = ViewModelProviders.of(this).get(MemoListViewModel::class.java)
        memoListViewModel.memoList.observe(this, Observer {
            if (it != null) {
                viewAdapter.setMemoList(it)
            }
        })
        memoListViewModel.deleteMemoState.observe(this, Observer {
            Snackbar.make(rootLayout, it, Snackbar.LENGTH_LONG).show()
        })
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    floatButton.apply {
                        this.setOnClickListener { v ->
                            if (isFabPlusIcon) {
                                openCreateMemoActivity(null)
                            } else {
                                for (deleteMemo in mDeleteMemos) {
                                    memoListViewModel.deleteMemo(deleteMemo)
                                }
                                mDeleteMemos = arrayListOf()
                                refreshMemoColor()
                                rotateFAB()
                            }
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
                                    changeCarColor(view, memo)
                                }
                            }

                            override fun onItemLongClick(view: View, memo: Memo): Boolean {
                                isDeleteMode = true

                                changeCarColor(view, memo)
                                rotateFAB()
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

    fun changeCarColor(card: View, memo: Memo) {
        if (card is MaterialCardView) {
            if (card.cardBackgroundColor == ColorStateList.valueOf(getColor(R.color.white))) {
                card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorAccent
                    )
                )
                mDeleteMemos.add(memo)
                mDeleteMemoViews.add(card)
            } else {
                card.setCardBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.white
                    )
                )
                mDeleteMemos.remove(memo)
                mDeleteMemoViews.remove(card)
                if (mDeleteMemos.size == 0) {
                    rotateFAB()
                    isDeleteMode=false
                }
            }

        }
    }

    fun refreshMemoColor() {
        for (view in mDeleteMemoViews) {
            if (view is MaterialCardView) {
                if (view.cardBackgroundColor == ColorStateList.valueOf(getColor(R.color.white))) {
                    view.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.colorAccent
                        )
                    )
                } else {
                    view.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.white
                        )
                    )
                }

            }
        }
        mDeleteMemoViews = arrayListOf()
    }

    fun rotateFAB() {
        if (!isDeleteMode) {
            ViewCompat.animate(float_button).rotation(360f).withLayer().setDuration(300L)
                .setInterpolator(
                    OvershootInterpolator()
                )
        }
        if (isFabPlusIcon) {
            float_button.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_delete_24dp
                )
            )
            isFabPlusIcon = false
        } else {
            float_button.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add))
            isFabPlusIcon = true
        }

    }

    interface MemoClickListener {
        fun onItemClick(view: View, memo: Memo)
        fun onItemLongClick(view: View, memo: Memo): Boolean
    }
}
