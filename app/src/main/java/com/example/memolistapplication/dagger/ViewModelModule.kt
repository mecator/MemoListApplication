package com.example.memolistapplication.dagger

import android.app.Application
import com.example.memolistapplication.viewmodel.GithubViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(val app:Application) {

    @Provides
    fun provideGithubViewModel():GithubViewModel{
        return GithubViewModel(app)
    }
}