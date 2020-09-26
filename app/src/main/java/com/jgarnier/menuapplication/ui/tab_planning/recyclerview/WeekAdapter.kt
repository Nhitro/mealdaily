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

    companion object {
        // Represents one century
        const val MAX_LENGTH = 36500;
    }

    // The current date is at the middle of the list
    val localDateNowIndex: Int = MAX_LENGTH / 2

    private var mLastSelectedWeekDate = WeekDate(mLocalDateNow, localDateNowIndex, true)

    override fun getItemCount(): Int = MAX_LENGTH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDateViewHolder {
        return WeekDateViewHolder(
            Consumer<WeekDate> { selectWeekDate(it) }.andThen(mSelectDateConsumer),
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
        if (!weekDate.localDate.isEqual(mLastSelectedWeekDate.localDate)) {
            // Keep the old index in order to reset the old selected cell
            val oldIndex = mLastSelectedWeekDate.position

            // Set the current selected cell
            mLastSelectedWeekDate = weekDate

            // And update the list
            notifyItemChanged(oldIndex)
            notifyItemChanged(weekDate.position)
        }
    }
}