package com.jgarnier.menuapplication.ui.tab_planning.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgarnier.menuapplication.R
import java.time.LocalDate
import java.util.function.Consumer

/**
 * Adapter in charge of managing the [com.jgarnier.menuapplication.ui.tab_planning.PlanningFragment] recycler view
 */
class WeekAdapter(
    private val mLocalDateNow: LocalDate,
    private val mSelectDateConsumer: Consumer<WeekDate>
) : RecyclerView.Adapter<WeekDateViewHolder>() {

    val localDateNowIndex: Int = itemCount / 2

    private var mLastSelectedWeekDate = WeekDate(mLocalDateNow, localDateNowIndex, true)

    override fun getItemCount(): Int = Integer.MAX_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDateViewHolder {
        return WeekDateViewHolder(
            mSelectDateConsumer.andThen { selectWeekDate(it) },
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.holder_week_date, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeekDateViewHolder, position: Int) {
        val localDate = mLocalDateNow.plusDays((position - localDateNowIndex).toLong())

        holder.update(
            WeekDate(localDate, position, localDate == mLastSelectedWeekDate.localDate)
        )
    }

    /**
     * Call this method when you want to change which cell is selected after a click
     */
    private fun selectWeekDate(weekDate: WeekDate) {
        // Keep the old index in order to reset the old selected cell
        val oldIndex = mLastSelectedWeekDate.position

        // Set the current selected cell
        mLastSelectedWeekDate = weekDate

        // And update the list
        notifyItemChanged(oldIndex)
        notifyItemChanged(weekDate.position)
    }
}