package com.jgarnier.menuapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true) val idFood: Int,
    val foodName: String
)