package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dish(
    @PrimaryKey(autoGenerate = true) val idDish: Int,
    val dishName: String,
    val dishDescription: String
)