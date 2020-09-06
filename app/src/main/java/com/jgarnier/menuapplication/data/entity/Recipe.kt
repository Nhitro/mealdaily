package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val idRecipe: Int,
    val recipeName: String,
    val recipeDescription: String
)