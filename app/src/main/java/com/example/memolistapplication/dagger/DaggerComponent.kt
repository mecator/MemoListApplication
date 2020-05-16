package com.example.memolistapplication.dagger

import com.example.memolistapplication.ui.GithubActivity
import dagger.Component

@Component(modules = [ViewModelModule::class])
interface DaggerComponent {
    fun inject (activity:GithubActivity)
}