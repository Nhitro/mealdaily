package com.jgarnier.menuapplication.ui.base

import android.widget.CalendarView
import java.time.LocalDate
import java.time.ZoneOffset

fun CalendarView.setLocalDate(localDate: LocalDate, animate: Boolean, center: Boolean) {
    this.setDate(
            localDate.atStartOfDay()
                    .toInstant(ZoneOffset.UTC)
                    .toEpochMilli(),
            animate,
            center
    )
}