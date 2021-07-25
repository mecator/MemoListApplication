package jp.mercator.memolistapplication.dagger

import android.app.Application
import jp.mercator.memolistapplication.viewmodel.GithubViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(val app:Application) {

    @Provides
    fun provideGithubViewModel():GithubViewModel{
        return GithubViewModel(app)
    }
}