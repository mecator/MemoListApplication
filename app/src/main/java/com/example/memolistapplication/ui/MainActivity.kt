package com.example.memolistapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memolistapplication.MemoApplication
import com.example.memolistapplication.R
import com.example.memolistapplication.room.Memo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager:RecyclerView.LayoutManager
    private lateinit var memoList:List<Memo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FloatingActionButton>(R.id.float_button).apply {
            this.setOnClickListener { v ->
                startActivity(Intent(applicationContext, CreateMemoActivity::class.java))
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
            }
        }
        viewManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val dao = MemoApplication.database.memoDao()
        GlobalScope.launch(Dispatchers.Default) {
            memoList= dao.findAll()
            viewAdapter=MemoListAdapter(baseContext,memoList)
            recyclerView=findViewById<RecyclerView>(R.id.memo_list).apply {
                layoutManager=viewManager
                adapter=viewAdapter
            }
        }
    }
}
