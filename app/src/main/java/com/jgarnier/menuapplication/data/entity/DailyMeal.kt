package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyMeal(
    @PrimaryKey(autoGenerate = true) val idDailyMeal: Int,
    val dailyMealYear: Int,
    val dailyMealMonth: Int,
    val dailyMealDay: Int,
    val lunchMealName: String,
    val dinnerMealName: String
)