package com.jgarnier.menuapplication.ui.tab_planning

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jgarnier.menuapplication.data.Result
import com.jgarnier.menuapplication.data.Result.Loading
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.data.repository.MealRepository
import com.jgarnier.menuapplication.ui.base.AbstractListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.time.LocalDate

/**
 * Represents the state of [PlanningFragment]
 */
@FlowPreview
@ExperimentalCoroutinesApi
class PlanningViewModel @ViewModelInject constructor(
    application: Application,
    private val mMealRepository: MealRepository
) : AbstractListViewModel<LocalDate, MealWithDishes>(application) {

    companion object {
        private const val LAST_DATE = "last_date_key_bundle"
        private const val TYPE_OF_VIEW = "last_type_of_view_key_bundle"
        private const val MEAL_AND_DISHES = "last_meals_and_dishes_key_bundle"
        const val CALENDAR_VIEW = 1
        const val WEEK_DAY_VIEW = 2
    }

    private val mSelectedLocalDate = MutableLiveData(LocalDate.now())

    private val mCurrentTypeView = MutableLiveData(WEEK_DAY_VIEW)

    private val mMealWithDishesList: MutableLiveData<Result<List<MealWithDishes>>> =
        MutableLiveData(Loading())

    val selectedLocalDate: LiveData<LocalDate>
        get() = mSelectedLocalDate

    val currentTypeView: LiveData<Int>
        get() = mCurrentTypeView

    init {
        userSelectedDate(mSelectedLocalDate.value!!)
    }

    fun userSelectedDate(localDate: LocalDate) {
        mSelectedLocalDate.postValue(localDate)
        mFilterObjectChannel.offer(localDate)
    }

    fun selectWeekView() = postTypeViewIfDifferent(WEEK_DAY_VIEW)
    fun selectMonthView() = postTypeViewIfDifferent(CALENDAR_VIEW)

    private fun postTypeViewIfDifferent(typeView: Int) {
        if (mCurrentTypeView.value != null && mCurrentTypeView.value != typeView) {
            mCurrentTypeView.postValue(typeView)
        }
    }

    override suspend fun fetchData(filterObject: LocalDate) =
        mMealRepository.getDailyMealAccording(filterObject)

}