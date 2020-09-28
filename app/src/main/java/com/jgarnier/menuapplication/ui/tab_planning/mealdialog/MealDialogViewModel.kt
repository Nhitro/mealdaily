package com.jgarnier.menuapplication.ui.tab_planning.mealdialog

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgarnier.menuapplication.data.raw.MealSort
import com.jgarnier.menuapplication.data.repository.MealRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class MealDialogViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository
) : ViewModel() {

    val closeDialog: MutableLiveData<Boolean> = MutableLiveData(false)

    private var mSelectedMealSort = MealSort.BREAK_FAST

    fun insert(localDate: LocalDate) {
        viewModelScope.launch {
            val order = mealRepository.getLastMealOrderOf(localDate) ?: 0
            mealRepository.insert(localDate, order, mSelectedMealSort)
            closeDialog.postValue(true)
        }
    }

    fun selectMeal(mealSort: MealSort) {
        mSelectedMealSort = mealSort
    }

}