package com.example.memolistapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.memolistapplication.R
import com.google.android.material.textfield.TextInputEditText

class CreateMemoFragment : Fragment() {
    companion object {
        fun createInstance(): CreateMemoFragment {
            val fragment = CreateMemoFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.fragment_create_memo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText=view.findViewById<TextInputEditText>(R.id.memo_text_field)
        view.findViewById<LinearLayout>(R.id.rootLayout).setOnClickListener {editText.clearFocus()  }
    }
}