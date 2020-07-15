package com.example.memolistapplication.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.memolistapplication.R
import com.example.memolistapplication.databinding.ActivityGithubBinding
import com.example.memolistapplication.databinding.ActivityMainBinding
import com.example.memolistapplication.viewmodel.GithubViewModel
import java.util.zip.Inflater
import javax.inject.Inject

class GithubActivity :AppCompatActivity(){
    @Inject lateinit var viewModel:GithubViewModel
    private lateinit var binding :ActivityGithubBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGithubBinding.inflate(LayoutInflater.from(applicationContext))
setContentView(binding.root)
binding.text2.visibility=VISIBLE
    }
}