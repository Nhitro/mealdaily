package com.jgarnier.menuapplication.ui.tab_planning.mealdialog

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jgarnier.menuapplication.data.repository.MealRepository
import java.time.LocalDate

class MealDialogViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val mealRepository: MealRepository
) : ViewModel() {

    fun insert(localDate: LocalDate) {

    }

}