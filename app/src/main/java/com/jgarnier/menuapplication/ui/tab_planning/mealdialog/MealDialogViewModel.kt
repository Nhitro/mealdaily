package com.jgarnier.menuapplication.ui.tab_planning.mealdialog

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.jgarnier.menuapplication.data.raw.MealSort
import com.jgarnier.menuapplication.data.repository.MealRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class MealDialogViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository
) : ViewModel() {

    private var mSelectedMealSort = MealSort.BREAK_FAST
    private val mCloseDialog: MutableLiveData<Boolean> = MutableLiveData(false)

    val closeDialog: LiveData<Boolean>
        get() = mCloseDialog

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