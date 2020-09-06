package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import com.jgarnier.menuapplication.data.AmountSort

@Entity(primaryKeys = ["idRecipe", "idFood"])
data class RecipeIngredient(
    val idRecipe: Int,
    val idFood: Int,
    val amount: Int,
    val amountSort: AmountSort
)