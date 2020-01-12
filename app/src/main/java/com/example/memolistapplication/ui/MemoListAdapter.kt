package com.example.memolistapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.CardMemoBinding
import com.example.memolistapplication.room.Memo

class MemoListAdapter(
    private val context: AppCompatActivity,
    private var memoList: List<Memo>
) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun getItemCount(): Int {
        return memoList.size
    }

    internal fun setMemoList(memoList: List<Memo>) {
        this.memoList = memoList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder?.let {
            val binding = holder.binding
            binding.lifecycleOwner = context
            holder.bind(memoList[position])
//            it.itemText.text = memoList[position].description
//            it.cardView.setOnClickListener {  }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<CardMemoBinding>(
            LayoutInflater.from(context), R.layout.card_memo, parent, false
        )
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.card_memo, parent, false)
        return RecyclerViewHolder(binding)
    }
}

class RecyclerViewHolder(val binding: CardMemoBinding) : RecyclerView.ViewHolder(binding.root) {

    interface MemoClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun bind(memo: Memo) {
        binding.memo = memo
    }
}