package com.jgarnier.menuapplication.ui.tab_planning.dishessearch

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jgarnier.menuapplication.data.entity.DishWithLines
import com.jgarnier.menuapplication.data.repository.DishRepository
import com.jgarnier.menuapplication.data.repository.MealRepository
import com.jgarnier.menuapplication.ui.base.AbstractListViewModel
import com.jgarnier.menuapplication.ui.base.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class DishesSearchViewModel @ViewModelInject constructor(
    application: Application,
    private val mDishRepository: DishRepository,
    private val mMealRepository: MealRepository
) : AbstractListViewModel<String, DishWithLines>(application) {

    private val backSingleLiveEvent = SingleLiveEvent<Boolean>()

    val closeLiveData: LiveData<Boolean>
        get() = backSingleLiveEvent

    init {
        mFilterObjectChannel.offer("")
    }

    override suspend fun fetchData(filterObject: String): Flow<List<DishWithLines>> =
        mDishRepository.findDishesWithLineMatching(filterObject)

    fun updateSearchFilter(text: CharSequence?) {
        mFilterObjectChannel.offer((text ?: "").toString())
    }

    fun userSelectedDish(mealId: Int, dishWithLines: DishWithLines) {
        viewModelScope.launch {
            mMealRepository.insert(mealId, dishWithLines.dish.idDish)
            backSingleLiveEvent.postValue(true)
        }
    }

}