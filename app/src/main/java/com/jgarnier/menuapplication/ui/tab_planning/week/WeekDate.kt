package com.jgarnier.menuapplication.ui.tab_planning.week

import java.time.LocalDate

/**
 * Representing date cell data
 */
data class WeekDate(
    val localDate: LocalDate,
    val position: Int,
    var isSelected: Boolean
)