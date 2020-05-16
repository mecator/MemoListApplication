package com.example.memolistapplication.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityGithubBinding
import com.example.memolistapplication.databinding.ActivityMainBinding
import com.example.memolistapplication.viewmodel.GithubViewModel
import javax.inject.Inject

class GithubActivity :AppCompatActivity(){
    @Inject lateinit var viewModel:GithubViewModel
    private  val binding by lazy{
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_github)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}