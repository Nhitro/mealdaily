package com.jgarnier.menuapplication.ui.tab_planning

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.data.repository.MealRepository
import com.jgarnier.menuapplication.ui.base.AbstractListViewModel
import com.jgarnier.menuapplication.ui.base.SingleLiveEvent
import com.jgarnier.menuapplication.ui.tab_planning.meals.SelectableMealWithDishes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map
import java.time.LocalDate

/**
 * Represents the state of [PlanningFragment]
 */
@FlowPreview
@ExperimentalCoroutinesApi
class PlanningViewModel @ViewModelInject constructor(
        application: Application,
        private val mMealRepository: MealRepository
) : AbstractListViewModel<LocalDate, SelectableMealWithDishes>(application) {

    companion object {
        const val CALENDAR_VIEW = 1
        const val WEEK_DAY_VIEW = 2
    }

    private val mSelectedLocalDate = MutableLiveData(LocalDate.now())

    private val mCurrentTypeView = SingleLiveEvent<Int>()

    private val mIsDeletingMode = SingleLiveEvent<Boolean>()

    private val mMealsSelected = ArrayList<MealWithDishes>()

    private val mMealsSelectedNumber = MutableLiveData(0)

    val selectedLocalDate: LiveData<LocalDate>
        get() = mSelectedLocalDate

    val currentTypeView: LiveData<Int>
        get() = mCurrentTypeView

    val isDeletingMode: LiveData<Boolean>
        get() = mIsDeletingMode

    val mealSelectedNumber: LiveData<Int>
        get() = mMealsSelectedNumber

    init {
        userSelectedDate(mSelectedLocalDate.value!!)
    }

    fun userSelectedDate(localDate: LocalDate) {
        mSelectedLocalDate.postValue(localDate)
        mFilterObjectChannel.offer(localDate)
    }

    fun userChangeStateOf(selectableMealWithDishes: SelectableMealWithDishes) {
        if (selectableMealWithDishes.isSelected) {
            if (mMealsSelected.isEmpty()) {
                mIsDeletingMode.postValue(true)
            }
            mMealsSelected.add(selectableMealWithDishes.mealWithDishes)
        } else {
            mMealsSelected.remove(selectableMealWithDishes.mealWithDishes)
            if (mMealsSelected.isEmpty()) {
                mIsDeletingMode.postValue(false)
            }
        }
    }

    fun selectWeekView() = postTypeViewIfDifferent(WEEK_DAY_VIEW)
    fun selectMonthView() = postTypeViewIfDifferent(CALENDAR_VIEW)

    private fun postTypeViewIfDifferent(typeView: Int) {
        if (mCurrentTypeView.value == null || mCurrentTypeView.value != typeView) {
            mCurrentTypeView.postValue(typeView)
        }
    }

    override suspend fun fetchData(filterObject: LocalDate) =
            mMealRepository
                    .getDailyMealAccording(filterObject)
                    .map { list ->
                        list.map { mealWithDishes ->
                            SelectableMealWithDishes(mealWithDishes, mMealsSelected.contains(mealWithDishes), false)
                        }
                    }
}