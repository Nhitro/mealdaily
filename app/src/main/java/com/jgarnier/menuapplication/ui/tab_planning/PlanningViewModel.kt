package com.jgarnier.menuapplication.ui.tab_planning

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgarnier.menuapplication.data.Result
import com.jgarnier.menuapplication.data.Result.Loading
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.data.repository.MealRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * Represents the state of [PlanningFragment]
 */
class PlanningViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository
) : ViewModel() {

    companion object {
        private const val LAST_DATE = "last_date_key_bundle"
        private const val TYPE_OF_VIEW = "last_type_of_view_key_bundle"
        private const val MEAL_AND_DISHES = "last_meals_and_dishes_key_bundle"
        const val CALENDAR_VIEW = 1
        const val WEEK_DAY_VIEW = 2
    }

    val selectedLocalDate: MutableLiveData<LocalDate>
    val currentTypeView: MutableLiveData<Int>
    val mealWithDishesList: MutableLiveData<Result<List<MealWithDishes>>>

    init {
        selectedLocalDate = savedStateHandle.get<MutableLiveData<LocalDate>>(LAST_DATE)
            ?: MutableLiveData(LocalDate.now())
        currentTypeView =
            savedStateHandle.get<MutableLiveData<Int>>(TYPE_OF_VIEW) ?: MutableLiveData(
                WEEK_DAY_VIEW
            )

        val savedMealWithDishes =
            savedStateHandle.get<MutableLiveData<Result<List<MealWithDishes>>>>(MEAL_AND_DISHES)

        if (savedMealWithDishes == null) {
            mealWithDishesList = MutableLiveData(Loading())
            userSelectedDate(selectedLocalDate.value!!)
        } else {
            mealWithDishesList = savedMealWithDishes
        }
    }

    override fun onCleared() {
        savedStateHandle.set(TYPE_OF_VIEW, currentTypeView)
        savedStateHandle.set(MEAL_AND_DISHES, mealWithDishesList)
        savedStateHandle.set(LAST_DATE, selectedLocalDate)
    }

    fun userSelectedDate(localDate: LocalDate) {
        selectedLocalDate.postValue(localDate)

        viewModelScope.launch {
            mealRepository
                .getDailyMealAccording(localDate)
                .collect { mealWithDishesList.postValue(Result.Success(it)) }
        }
    }

    fun selectWeekView() = postTypeViewIfDifferent(WEEK_DAY_VIEW)
    fun selectMonthView() = postTypeViewIfDifferent(CALENDAR_VIEW)

    private fun postTypeViewIfDifferent(typeView: Int) {
        if (currentTypeView.value != null && currentTypeView.value != typeView) {
            currentTypeView.postValue(typeView)
        }
    }

}