package com.jgarnier.menuapplication.ui.daily

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgarnier.menuapplication.data.entity.DailyMeal
import com.jgarnier.menuapplication.data.repository.DailyMealRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * ViewModel representing the state of [DailyMenuFragment]
 */
class DailyMenuViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val dailyMealRepository: DailyMealRepository
) : ViewModel(), LifecycleObserver {

    val dailyMenuState = DailyMenuState.dailyMenuLoadingState()

    var selectedDate: LocalDate = LocalDate.now()

    fun retryFetchMeal() = fetchMealAccording(selectedDate)

    fun fetchMealAccording(localDate: LocalDate) {
        selectedDate = localDate
        dailyMenuState.isLoading()

        viewModelScope.launch {

            dailyMealRepository
                .getDailyMealAccording(localDate)
                .catch {
                    dailyMenuState.encounteredError()
                }
                .collect {
                    val dailyMeal =  it?: DailyMeal(-1, -1, -1, -1, "", "")
                    dailyMenuState.showDailyMeal(getCapitalizeDate(localDate), dailyMeal.lunchMealName, dailyMeal.dinnerMealName)
                }
        }
    }

    /**
     * @return localDate into "EEEE dd MMMM YYYY" format and capitalize each word (exp : Tuesday 14 September 2020)
     */
    private fun getCapitalizeDate(localDate: LocalDate): String {
        return localDate.format(DateTimeFormatter.ofPattern("EEEE dd MMMM YYYY"))
            .split(" ")
            .joinToString(" ") { it.capitalize() }
    }

}