package com.example.memolistapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.CardMemoBinding
import com.example.memolistapplication.room.Memo

class MemoListAdapter(
    private val context: AppCompatActivity,
    private var memoList: List<Memo>,
    private val clickListener: MainActivity.MemoClickListener
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
        holder.apply {
            binding.lifecycleOwner = context
            bind(memoList[position], clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<CardMemoBinding>(
            LayoutInflater.from(context), R.layout.card_memo, parent, false
        )
        return RecyclerViewHolder(binding)
    }
}

class RecyclerViewHolder(val binding: CardMemoBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(memo: Memo, listener: MainActivity.MemoClickListener) {
        binding.memo = memo
        binding.clickListener = listener
    }
}