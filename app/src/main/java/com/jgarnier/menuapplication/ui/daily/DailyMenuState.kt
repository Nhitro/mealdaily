package com.jgarnier.menuapplication.ui.daily

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

/**
 * Represents [DailyMenuFragment] state
 */
data class DailyMenuState(
    private var mCurrentDate: String,
    private var mLunchDescription: String,
    private var mDinnerDescription: String,
    private var mDailyMenuDataVisibility: Int,
    private var mLoaderVisibility: Int,
    private var mErrorVisibility: Int
) : BaseObservable() {

    companion object {

        /**
         * @return a [DailyMenuState] showing the progress bar
         */
        fun dailyMenuLoadingState() = DailyMenuState("", "", "", View.VISIBLE, View.VISIBLE, View.GONE)

    }

    /**
     * change the state in order to show the progress bar
     */
    fun isLoading() {
        loaderVisibility = View.VISIBLE
        errorVisibility = View.GONE
    }

    /**
     * change the state in order to show error message and retry button
     */
    fun encounteredError() {
        loaderVisibility = View.GONE
        dailyMenuDataVisibility = View.INVISIBLE
        errorVisibility = View.VISIBLE
    }

    /**
     * change the state in order to hide progress bar and show daily meal information
     */
    fun showDailyMeal(dateToShow: String, lunchDescriptionToShow: String, dinnerDescriptionToSHow: String) {
        loaderVisibility = View.GONE
        dailyMenuDataVisibility = View.VISIBLE
        currentDate = dateToShow
        lunchDescription = lunchDescriptionToShow
        dinnerDescription = dinnerDescriptionToSHow
    }

    var currentDate: String
        @Bindable get() = mCurrentDate
        set(currentDate) {
            mCurrentDate = currentDate
            notifyPropertyChanged(BR.currentDate)
        }

    var lunchDescription: String
        @Bindable get() = mLunchDescription
        set(lunchDescription) {
            mLunchDescription = lunchDescription
            notifyPropertyChanged(BR.lunchDescription)
        }

    var dinnerDescription: String
        @Bindable get() = mDinnerDescription
        set(dinnerDescription) {
            mDinnerDescription = dinnerDescription
            notifyPropertyChanged(BR.dinnerDescription)
        }

    var dailyMenuDataVisibility: Int
        @Bindable get() = mDailyMenuDataVisibility
        set(dailyMenuDataVisibility) {
            mDailyMenuDataVisibility = dailyMenuDataVisibility
            notifyPropertyChanged(BR.dailyMenuDataVisibility)
        }

    var loaderVisibility: Int
        @Bindable get() = mLoaderVisibility
        set(loaderVisibility) {
            mLoaderVisibility = loaderVisibility
            notifyPropertyChanged(BR.loaderVisibility)
        }

    var errorVisibility: Int
        @Bindable get() = mErrorVisibility
        set(errorVisibility) {
            mErrorVisibility = errorVisibility
            notifyPropertyChanged(BR.errorVisibility)
        }
}