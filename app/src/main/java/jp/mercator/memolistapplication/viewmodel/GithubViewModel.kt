package jp.mercator.memolistapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class GithubViewModel @Inject constructor(app:Application): AndroidViewModel(app) {
    override fun onCleared() {
        super.onCleared()
    }
}