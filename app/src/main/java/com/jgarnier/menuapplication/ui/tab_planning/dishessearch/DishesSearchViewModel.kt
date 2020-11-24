package com.jgarnier.menuapplication.ui.tab_planning.dishessearch

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.jgarnier.menuapplication.data.entity.DishWithLines
import com.jgarnier.menuapplication.data.repository.DishRepository
import com.jgarnier.menuapplication.ui.base.AbstractListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
@ExperimentalCoroutinesApi
class DishesSearchViewModel @ViewModelInject constructor(
        application: Application,
        private val mDishRepository: DishRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : AbstractListViewModel<String, DishWithLines>(application) {

    init {
        mFilterObjectChannel.offer("")
    }

    override suspend fun fetchData(filterObject: String): Flow<List<DishWithLines>> = mDishRepository.findDishesWithLineMatching(filterObject)

    fun updateSearchFilter(text: CharSequence?) {
        mFilterObjectChannel.offer((text ?: "").toString())
    }

}