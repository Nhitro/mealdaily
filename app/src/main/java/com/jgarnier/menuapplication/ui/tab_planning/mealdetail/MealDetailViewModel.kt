package com.jgarnier.menuapplication.ui.tab_planning.mealdetail

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.data.entity.Dish
import com.jgarnier.menuapplication.data.entity.MealWithDishes
import com.jgarnier.menuapplication.data.raw.MealSort
import com.jgarnier.menuapplication.data.repository.DishRepository
import com.jgarnier.menuapplication.data.repository.MealRepository
import com.jgarnier.menuapplication.ui.base.AbstractListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ExperimentalCoroutinesApi
@FlowPreview
class MealDetailViewModel @ViewModelInject constructor(
        application: Application,
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val mMealRepository: MealRepository,
        private val mDishRepository: DishRepository
) : AbstractListViewModel<Int, Dish>(application) {

    private val mealTitleLiveData: MutableLiveData<String> = MutableLiveData()

    private var mealWithDishes: MealWithDishes? = null

    val mealTitle: LiveData<String>
        get() = mealTitleLiveData

    override suspend fun fetchData(filterObject: Int): Flow<List<Dish>> {
        return mDishRepository.getDishesAssociatedToMeal(filterObject)
    }

    fun fetchMealAndDishes(day: Int, month: Int, year: Int, mealSort: String) {
        val mealSortConverted = MealSort.valueOf(mealSort)
        computeTitle(day, month, year, mealSortConverted)
        viewModelScope.launch {
            mMealRepository.findMealWithDishes(day, month, year, mealSortConverted).apply {
                mealWithDishes = this
                mFilterObjectChannel.offer(this.meal.idMeal)
            }
        }
    }

    private fun computeTitle(day: Int, month: Int, year: Int, mealSortConverted: MealSort) {
        val context = getApplication<Application>()
        val date = getCapitalizeDate(LocalDate.of(year, month, day))
        val idMessage =
                when (mealSortConverted) {
                    MealSort.BREAK_FAST -> R.string.meal_break_fast
                    MealSort.LUNCH -> R.string.meal_lunch
                    MealSort.DINNER -> R.string.meal_dinner
                    MealSort.SNACK -> R.string.meal_snack
                }

        mealTitleLiveData.postValue(
                context.getString(
                        R.string.detail_meal_title,
                        context.getString(idMessage),
                        date
                )
        )
    }

    private fun getCapitalizeDate(localDate: LocalDate): String {
        return localDate.format(DateTimeFormatter.ofPattern("EE dd MMM"))
                .split(" ")
                .joinToString(" ") { it.capitalize() }
    }

}