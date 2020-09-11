package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jgarnier.menuapplication.data.raw.MealSort

@Entity
data class Meal(
    @PrimaryKey(autoGenerate = true) val idMeal: Int,
    val mealYear: Int,
    val mealMonth: Int,
    val mealDay: Int,
    val mealSort: MealSort
)