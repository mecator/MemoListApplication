package com.example.memolistapplication.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.memolistapplication.databinding.CustomViewBinding;


public class CustomView extends LinearLayout {

    private CustomViewBinding binding;
    public CustomView(Context context){
        this(context,null);
    }
    public CustomView(Context context,  AttributeSet attrs){
        this(context,attrs,0);
    }
    public CustomView(Context context,  AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding=CustomViewBinding.inflate(inflater,this,true);
        binding.text.setText("helloworld");
    }


}
