package com.jgarnier.menuapplication.ui.tab_planning

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jgarnier.menuapplication.ui.tab_planning.recyclerview.WeekDate

/**
 * Represents the state of [PlanningFragment]
 */
class PlanningViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TYPE_OF_VIEW = "select_date_key_bundle"
        const val CALENDAR_VIEW = 1
        const val WEEK_DAY_VIEW = 2
    }

    val mCurrentTypeView: MutableLiveData<Int>

    init {
        mCurrentTypeView = savedStateHandle.get<MutableLiveData<Int>>(TYPE_OF_VIEW) ?: MutableLiveData(WEEK_DAY_VIEW)
    }

    override fun onCleared() {
        savedStateHandle.set(TYPE_OF_VIEW, mCurrentTypeView)
    }

    fun userSelectedDate(weekDate: WeekDate) {
        // TODO: Implement this method
    }

    fun selectWeekView() = postTypeViewIfDifferent(WEEK_DAY_VIEW)
    fun selectMonthView() = postTypeViewIfDifferent(CALENDAR_VIEW)

    private fun postTypeViewIfDifferent(typeView: Int) {
        if (mCurrentTypeView.value != null && mCurrentTypeView.value != typeView) {
            mCurrentTypeView.postValue(typeView)
        }
    }

}