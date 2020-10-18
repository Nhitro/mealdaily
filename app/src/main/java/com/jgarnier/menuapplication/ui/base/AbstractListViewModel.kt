package com.jgarnier.menuapplication.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.jgarnier.menuapplication.data.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
abstract class AbstractListViewModel<T, U>(application: Application) :
    AndroidViewModel(application) {

    protected val mFilterObjectChannel = ConflatedBroadcastChannel<T>()

    private val mFetchedDataAfterFiltered =
        mFilterObjectChannel
            .asFlow()
            .flatMapLatest { filterObject -> fetchData(filterObject) }
            .map { value -> Result.Success(value) as Result<List<U>> }
            .catch { cause -> Result.Error<List<U>>(cause.message) }
            .asLiveData()

    val fetchedData: LiveData<Result<List<U>>>
        get() = mFetchedDataAfterFiltered

    protected abstract suspend fun fetchData(filterObject: T): Flow<List<U>>
}