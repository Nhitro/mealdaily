package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jgarnier.menuapplication.data.raw.AmountSort

@Entity
data class FoodLine(
    @PrimaryKey(autoGenerate = true) val idFoodLine: Int,
    val idMeal: Int,
    val idFood: Int,
    val amount: Int,
    val amountSort: AmountSort
)