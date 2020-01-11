package com.example.memolistapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityMainBinding
import com.example.memolistapplication.room.Memo

class MemoListAdapter(private val context: Context, private var memoList: List<Memo>) :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    private var mRecyclerView: RecyclerView? = null

    override fun getItemCount(): Int {
        return memoList.size
    }

   internal fun setMemoList(memoList: List<Memo>) {
        this.memoList = memoList
       notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder?.let { it.itemText.text = memoList[position].description }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.card_memo, parent, false)
        return RecyclerViewHolder(view)
    }
}
class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view){

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemText: TextView = view.findViewById(R.id.memo_description)

    init {
        itemText.setText("めもめおももえ")
    }
}