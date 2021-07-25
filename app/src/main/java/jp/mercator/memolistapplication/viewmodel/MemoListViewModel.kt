package jp.mercator.memolistapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import jp.mercator.memolistapplication.MemoApplication
import jp.mercator.memolistapplication.repository.MemoRepository
import jp.mercator.memolistapplication.repository.MemoRepositoryImpl
import jp.mercator.memolistapplication.room.Memo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MemoListViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: MemoRepositoryImpl
    var memoList: LiveData<List<Memo>>
    private var _deleteMemoState = MutableLiveData<String>()
    val deleteMemoState: LiveData<String>
        get() = _deleteMemoState
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val memoDao = MemoApplication.database.memoDao()
        repository = MemoRepository(memoDao)
        memoList = repository.memoList
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun deleteMemo(memo: Memo) {
        scope.launch(Dispatchers.IO) {
            runCatching { repository.delete(memo) }.fold(
                onFailure = { setDeleteState("メモの消去に失敗しました") },
                onSuccess = { setDeleteState("メモの消去に成功しました") })
        }
    }

    private suspend fun setDeleteState(message: String) {
        withContext(Dispatchers.Main) {
            _deleteMemoState.value = message
        }
    }
}