package com.jgarnier.menuapplication.ui.tab_planning

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.jgarnier.menuapplication.data.Result
import com.jgarnier.menuapplication.data.Result.Loading
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.data.repository.MealRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate

/**
 * Represents the state of [PlanningFragment]
 */
@FlowPreview
@ExperimentalCoroutinesApi
class PlanningViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val mMealRepository: MealRepository
) : ViewModel() {

    companion object {
        private const val LAST_DATE = "last_date_key_bundle"
        private const val TYPE_OF_VIEW = "last_type_of_view_key_bundle"
        private const val MEAL_AND_DISHES = "last_meals_and_dishes_key_bundle"
        const val CALENDAR_VIEW = 1
        const val WEEK_DAY_VIEW = 2
    }

    private val mSelectedLocalDate: MutableLiveData<LocalDate>

    private val mCurrentTypeView: MutableLiveData<Int>

    private val mMealWithDishesList: MutableLiveData<Result<List<MealWithDishes>>>

    private val mFetchDateChannel = ConflatedBroadcastChannel<LocalDate>()

    val selectedLocalDate: LiveData<LocalDate>
        get() = mSelectedLocalDate

    val currentTypeView: LiveData<Int>
        get() = mCurrentTypeView

    val mealWithDishes =
        mFetchDateChannel
            .asFlow()
            .flatMapLatest { date ->
                mMealRepository.getDailyMealAccording(date)
            }
            .map { value ->
                Result.Success(value)
            }
            .catch { cause -> Result.Error<List<MealWithDishes>>(cause.message) }
            .asLiveData()

    init {
        mSelectedLocalDate = savedStateHandle.get<MutableLiveData<LocalDate>>(LAST_DATE)
            ?: MutableLiveData(LocalDate.now())

        mCurrentTypeView =
            savedStateHandle.get<MutableLiveData<Int>>(TYPE_OF_VIEW) ?: MutableLiveData(
                WEEK_DAY_VIEW
            )

        val savedMealWithDishes =
            savedStateHandle.get<MutableLiveData<Result<List<MealWithDishes>>>>(MEAL_AND_DISHES)

        if (savedMealWithDishes == null) {
            mMealWithDishesList = MutableLiveData(Loading())
            userSelectedDate(mSelectedLocalDate.value!!)
        } else {
            mMealWithDishesList = savedMealWithDishes
        }
    }

    override fun onCleared() {
        savedStateHandle.set(TYPE_OF_VIEW, mCurrentTypeView)
        savedStateHandle.set(MEAL_AND_DISHES, mMealWithDishesList)
        savedStateHandle.set(LAST_DATE, mSelectedLocalDate)
    }

    fun userSelectedDate(localDate: LocalDate) {
        mSelectedLocalDate.postValue(localDate)
        mFetchDateChannel.offer(localDate)
    }

    fun selectWeekView() = postTypeViewIfDifferent(WEEK_DAY_VIEW)
    fun selectMonthView() = postTypeViewIfDifferent(CALENDAR_VIEW)

    private fun postTypeViewIfDifferent(typeView: Int) {
        if (mCurrentTypeView.value != null && mCurrentTypeView.value != typeView) {
            mCurrentTypeView.postValue(typeView)
        }
    }

}