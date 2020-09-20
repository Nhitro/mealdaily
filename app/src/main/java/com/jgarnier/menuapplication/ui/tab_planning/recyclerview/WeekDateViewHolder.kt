package com.jgarnier.menuapplication.ui.tab_planning.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jgarnier.menuapplication.R
import com.jgarnier.menuapplication.databinding.HolderWeekDateBinding
import java.time.format.TextStyle
import java.util.*
import java.util.function.Consumer

/**
 * Representing a date cell within [WeekAdapter]
 */
class WeekDateViewHolder(private val mSelectWeekDateConsumer: Consumer<WeekDate>, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mBinding = HolderWeekDateBinding.bind(itemView)

    /**
     * Call this method in order to update the view holder according new [weekDate]
     * @param weekDate                  is the new data to show
     */
    fun update(weekDate: WeekDate) {
        val currentLocale = Locale.getDefault()
        val localDate = weekDate.localDate

        // Setup data and color
        mBinding.dayNumber.text = localDate.dayOfMonth.toString()
        mBinding.dayName.text = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, currentLocale)
        mBinding.dayMonthName.text = localDate.month.getDisplayName(TextStyle.SHORT, currentLocale)

        mBinding.dayLayout.setBackgroundColor(
            itemView.context.getColor(
                if (weekDate.isSelected) {
                    R.color.primaryColor
                } else {
                    android.R.color.white
                }
            )
        )

        // Setup listener
        mBinding.dayLayout.setOnClickListener { mSelectWeekDateConsumer.accept(weekDate) }
    }

}