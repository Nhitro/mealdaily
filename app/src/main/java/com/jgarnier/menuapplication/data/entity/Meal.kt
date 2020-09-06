package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jgarnier.menuapplication.data.raw.MealSort
import java.util.*

@Entity
data class Meal(
    @PrimaryKey(autoGenerate = true) val idMeal: Int,
    val mealDate: Date,
    val mealSort: MealSort
)